package com.main.java.crawlerwebservice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CandlePattern {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String patternName;

    private String patternImgUrl;
    private String indication;
    private String reliability;
    private String description;

    public String getPatternID() {
        return patternName;
    }

    public void setPatternID(String patternID) {
        this.patternName = patternID;
    }

    public String getPatternImgUrl() {
        return patternImgUrl;
    }

    public void setPatternImgUrl(String patternImgUrl) {
        this.patternImgUrl = patternImgUrl;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public String getReliability() {
        return reliability;
    }

    public void setReliability(String reliability) {
        this.reliability = reliability;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
