package com.example.nalsam.chatbot.dto.request;


import lombok.Getter;

public class HealthRequest {
    public String getKeyword() {
        return keyword;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public int getOxyzenSaturation() {
        return oxyzenSaturation;
    }

    private String keyword;
    private int heartRate;
    private int oxyzenSaturation;

}
