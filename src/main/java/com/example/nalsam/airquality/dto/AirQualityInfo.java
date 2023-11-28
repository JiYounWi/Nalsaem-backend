package com.example.nalsam.airquality.dto;

import com.example.nalsam.airquality.util.AirQualityGrade;
import com.example.nalsam.airquality.util.AirQualityGradeUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@Getter
@Builder
public class AirQualityInfo {
    private String sidoCode;
    private List<GuAirQualityInfo> guList;

    public AirQualityInfo searchByGu(String gu) {
        if (gu == null) {
            return this;
        }
        var searchedGuInfo = searchGuAirQualityInfo(gu);
        this.guList = Collections.singletonList(searchedGuInfo);
        return this;
    }

    private GuAirQualityInfo searchGuAirQualityInfo(String gu) {
        return this.guList.stream()
                .filter(guAirQualityInfo -> guAirQualityInfo.getGu().equals(gu))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(gu + "에 해당하는 자치구가 존재하지 않습니다."));
    }

    @Getter
    public static class GuAirQualityInfo{
        private String gu;
        private String so2Value;
        private String coValue;
        private String pm10Value;
        private String pm25Value;
        private String no2Value;
        private String o3Value;

        private AirQualityGrade so2Grade ;
        private AirQualityGrade o3Grade ;
        private AirQualityGrade no2Grade;
        private AirQualityGrade pm25Grade;
        private AirQualityGrade coGrade;
        private AirQualityGrade pm10Grade ;


        public GuAirQualityInfo(String gu,String so2Value,String coValue,String pm10Value,String pm25Value,String no2Value,String o3Value){
            this.gu = gu;
            this.so2Value = so2Value;
            this.coValue = coValue;
            this.pm10Value = pm10Value;
            this.pm25Value = pm25Value;
            this.no2Value = no2Value;
            this.o3Value = o3Value;
            this.so2Grade = AirQualityGradeUtil.getSo2Grade(Double.valueOf(so2Value));
            this.coGrade = AirQualityGradeUtil.getCoGrade(Double.valueOf(coValue));
            this.pm10Grade = AirQualityGradeUtil.getPm10Grade(Double.valueOf(pm10Value));
            this.pm25Grade = AirQualityGradeUtil.getPm25Grade(Double.valueOf(pm25Value));
            this.no2Grade = AirQualityGradeUtil.getNo2Grade(Double.valueOf(no2Value));
            this.o3Grade =  AirQualityGradeUtil.getO3Grade(Double.valueOf(o3Value));
        }
    }
}
