package com.example.nalsam.user.dto.response;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse {

    private String loginId;

    private String userName;

    private String birthDate;

    private Integer isMale;

    private String symptom;

    private Integer heartRate;

    private Integer oxygenSaturation;


    @Builder
    public UserResponse(String loginId, String userName,String birthDate, Integer isMale, String symptom,Integer heartRate, Integer oxygenSaturation) {
        this.loginId = loginId;
        this.userName = userName;
        this.birthDate = birthDate;
        this.isMale = isMale;
        this.symptom = symptom;
        this.heartRate = heartRate;
        this.oxygenSaturation = oxygenSaturation;
    }

//    public static UserResponse of(){
//
//    }
}
