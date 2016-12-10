package com.main.java.crawlerwebservice;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

public class SendPostRequest {

    private final static String USER_AGENT = "Mozilla/5.0";

    // HTTP POST request
    static void sendPost(Object object) throws Exception {

        String url = "http://localhost:8090/insertcandle";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type", "application/xml");



        String urlParameters = null;
        if (object instanceof Indices) {
            final RuntimeTypeAdapterFactory<Indices> typeFactory = RuntimeTypeAdapterFactory
                    .of(Indices.class, "type");
            final Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(typeFactory).create();
            Indices indices = (Indices) object;
            urlParameters = gson.toJson(indices);
        } else if (object instanceof CandleStick) {
            final Gson gson = new GsonBuilder().create();
            CandleStick candleStick = (CandleStick) object;
            urlParameters = gson.toJson(candleStick);
        } else if (object instanceof CandlePattern) {
            final Gson gson = new GsonBuilder().create();
            CandlePattern candlePattern = (CandlePattern) object;
            urlParameters = gson.toJson(candlePattern);
        }

        // 2. Java object to JSON, and assign to a String


        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // print result
        System.out.println(response.toString());

    }

}