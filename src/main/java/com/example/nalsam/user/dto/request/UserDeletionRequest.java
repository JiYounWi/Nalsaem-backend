package com.example.nalsam.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDeletionRequest {

    private String loginId;

    private String password;

    @Builder
    public UserDeletionRequest(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}