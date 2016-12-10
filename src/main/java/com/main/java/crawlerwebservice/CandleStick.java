package com.main.java.crawlerwebservice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CandleStick {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private CandlePattern patternName;

    @ManyToOne
    private Indices index;

    private String timeFrame;
    private int candlesAgo;

    public CandlePattern getPatternName() {
        return patternName;
    }

    public void setPatternName(CandlePattern patternName) {
        this.patternName = patternName;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    public int getCandlesAgo() {
        return candlesAgo;
    }

    public void setCandlesAgo(int candlesAgo) {
        this.candlesAgo = candlesAgo;
    }

    public void setIndex(Indices indices) {
        // TODO Auto-generated method stub

    }

}

