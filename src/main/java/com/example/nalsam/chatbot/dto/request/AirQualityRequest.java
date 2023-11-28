package com.example.nalsam.chatbot.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class AirQualityRequest {
    private String keyword;

    private String so2Value;
    private String coValue;
    private String pm10Value;
    private String pm25Value;
    private String no2Value;
    private String o3Value;

    public String getKeyword() {
        return keyword;
    }

    public String getSo2Value() {
        return so2Value;
    }

    public String getCoValue() {
        return coValue;
    }

    public String getPm10Value() {
        return pm10Value;
    }

    public String getPm25Value() {
        return pm25Value;
    }

    public String getNo2Value() {
        return no2Value;
    }

    public String getO3Value() {
        return o3Value;
    }
}
