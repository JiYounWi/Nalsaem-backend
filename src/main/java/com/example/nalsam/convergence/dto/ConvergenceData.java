package com.example.nalsam.convergence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@NoArgsConstructor
//@AllArgsConstructor
public class ConvergenceData {
    //대기질 데이터
    private String pm10Grade ;  //미세먼지
    private String pm25Grade;   //초미세먼지
    private String so2Grade ;   //아황산가스
    private String o3Grade ;    //오존
    private String no2Grade;    //이산화질소
    private String coGrade;     //일산화탄소

    //날씨 데이터
    private Double temperature; //기온
    private int precipitation; //강수량
    private int humidity;       //습도

    //사용자 데이터
    private String userName;
    private Integer age; //나이
    private Integer heartRate; //심박수
    private Integer oxygenSaturation; // 산소포화도
    private String symptom; //증상

    @Builder
    public ConvergenceData(String pm10Grade, String pm25Grade, String so2Grade, String o3Grade, String no2Grade,
                           String coGrade, Double temperature, int precipitation, int humidity,String userName, Integer age,
                           Integer heartRate, Integer oxygenSaturation, String symptom) {
        this.pm10Grade = pm10Grade;
        this.pm25Grade = pm25Grade;
        this.so2Grade = so2Grade;
        this.o3Grade = o3Grade;
        this.no2Grade = no2Grade;
        this.coGrade = coGrade;
        this.temperature = temperature;
        this.precipitation = precipitation;
        this.humidity = humidity;
        this.userName = userName;
        this.age = age;
        this.heartRate = heartRate;
        this.oxygenSaturation = oxygenSaturation;
        this.symptom = symptom;
    }
}
