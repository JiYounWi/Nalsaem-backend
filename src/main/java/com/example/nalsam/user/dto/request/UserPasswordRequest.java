package com.example.nalsam.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPasswordRequest {

    private String loginId;

    private String password;

    private String newPassword;

    private String againNewPassword;

    @Builder
    public UserPasswordRequest(String loginId, String password, String newPassword, String againNewPassword) {
        this.loginId = loginId;
        this.password = password;
        this.newPassword = newPassword;
        this.againNewPassword = againNewPassword;
    }
}
