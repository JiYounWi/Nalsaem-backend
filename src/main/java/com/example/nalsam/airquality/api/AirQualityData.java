package com.example.nalsam.airquality.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
public class AirQualityData {
    @Getter
    @Setter
    @ToString
    public static class GetAirQualityResponse{
        private Response response;
    }

    @Getter
    @Setter
    @ToString
    public static class Response{
        private Body body;
        private Header header;

        public boolean isSuccess() {
            if (Objects.equals(header.getResultCode(), "00")) {
                return true;
            }
            return false;
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Body{
        private Integer totalCount;
        private List<Item> items;
        private Integer pageNo;
        private Integer numOfRows;
    }

    @Getter
    @Setter
    @ToString
    public static class Header{
        private String resultMsg;
        private String resultCode;
    }

    @Getter
    @Setter
    @ToString
    public static class Item{
        private String sidoName;
        private String stationName;
        private String dataTime;

        private String so2Value;
        private String coValue;
        private String pm10Value;
        private String pm25Value;
        private String no2Value;
        private String o3Value;


    }
}
