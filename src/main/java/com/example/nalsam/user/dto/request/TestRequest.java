package com.example.nalsam.user.dto.request;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestRequest {

    private String loginId;

    @Builder
    public TestRequest(String loginId) {
        this.loginId = loginId;
    }
}
