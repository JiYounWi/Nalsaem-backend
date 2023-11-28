package com.example.nalsam.chatbot.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class PlaceRecommend {
    private String keyword;
    private String sido;
    private String gu;

    public String getKeyword() {
        return keyword;
    }

    public String getSido() {
        return sido;
    }

    public String getGu() {
        return gu;
    }
}
