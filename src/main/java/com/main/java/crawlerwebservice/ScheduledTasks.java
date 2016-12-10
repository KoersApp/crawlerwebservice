package com.main.java.crawlerwebservice;

import java.io.File;
import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat sdf = new SimpleDateFormat(
            "ddMMyymmssSSS");
      
    public static void main(String[] args) {
        try {
            reportCurrentTime("gold");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Scheduled(fixedRate = 500000)
    public static void reportCurrentTime(String websiteLink)
            throws NumberFormatException, Exception {

        // Document doc = Jsoup
        // .connect("http://www.investing.com/commodities/" + websiteLink)
        // .data("query", "Java").userAgent("Mozilla")
        // .cookie("auth", "token").timeout(3000).get();
        // PrintWriter out = new PrintWriter(websiteLink + ".txt");
        // out.println(doc.toString());
        File input = new File(websiteLink + "-candlestick.txt");
        Document doc = Jsoup.parse(input, "UTF-8",
                "http://www.investing.com/commodities/"
                        + websiteLink + "-candlestick");

        Elements pageContent = doc.getElementsByTag("script");
        for (Element clas : pageContent) {
            String element = clas.toString();
            if (element
                    .contains("Patterns.patternsData[0] = {")) {
                System.out.println(element);
                String[] splittedElements = element
                        .split("Patterns.patternsData");
                for (int i = 0; i < splittedElements.length; i++) {
                    String imageUrl = findStringValue(splittedElements, i,
                            "img");
                    String type = findStringValue(splittedElements, i, "type");
                    String reliability = findStringValue(splittedElements, i,
                            "reliability");
                    String desc = findStringValue(splittedElements, i, "desc");
                    String time = findStringValue(splittedElements, i, "time");
                    String candle = findStringValue(splittedElements, i,
                            "candle");
                    String category = findStringValue(splittedElements, i,
                            "category");

                    CandlePattern candlePattern = new CandlePattern();
                    candlePattern.setDescription(desc);
                    candlePattern.setReliability(reliability);
                    candlePattern.setPatternID(type);

                    Indices indices = new Indices();
                    indices.setIndexName(websiteLink);
                    indices.setIndexImgUrl("/" + websiteLink);

                    CandleStick candleStick = new CandleStick();
                    candleStick.setCandlesAgo(getNumbericValue(candle));
                    candleStick.setIndex(indices);
                    candleStick.setTimeFrame(time);
                    candleStick.setPatternName(candlePattern);

                    SendPostRequest.sendPost(candleStick);

                }
            }


            // Product product = makeAProduct(brand, model, price, formFactor,
            // graphic, socket, imageUrl, rating);
            // SendPostRequest.sendPost(product);
            // PrintWriter out2 = null;
            // try {
            // out2 = new PrintWriter("filename2.txt");
            // } catch (FileNotFoundException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }
            // out2.println(product.getImageUrl());
        }

        // PrintWriter out = new PrintWriter("filename.txt");
        // out.println(doc.toString());


    }

    public static int getNumbericValue(String candle) {
        try {
            return Integer.parseInt(candle);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String findStringValue(String[] splittedElements, int i,
            String keyString) {
        String imageUrl = null;
        int imageIndex = splittedElements[i]
                .lastIndexOf(keyString);
        if (imageIndex > 0) {
            int endIndex = splittedElements[i].indexOf(",", imageIndex);
            if (endIndex < 1) {
                endIndex = splittedElements[i].indexOf(
                        System.getProperty("line.separator"), imageIndex);
                if (endIndex < 1) {
                    endIndex = splittedElements[i].indexOf("/n", imageIndex);
                    if (endIndex < 1) {
                        endIndex = splittedElements[i].indexOf("/r",
                                imageIndex);
                    }
                }
            }
            if (endIndex > 0) {
                imageUrl = splittedElements[i].substring(imageIndex, endIndex);
            }
        }
        return imageUrl;
    }


}