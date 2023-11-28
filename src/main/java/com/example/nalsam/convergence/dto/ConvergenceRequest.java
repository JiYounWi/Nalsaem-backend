package com.example.nalsam.convergence.dto;

import lombok.Getter;

@Getter
public class ConvergenceRequest {

    private String loginId;

    //대기질 데이터
    private String pm10Grade;  //미세먼지
    private String pm25Grade;   //초미세먼지
    private String so2Grade;
    private String o3Grade;
    private String no2Grade;
    private String coGrade;

    //날씨 데이터
    private Double temperature; //기온
    private int precipitation; //강수량
    private int humidity;       //습도

}
