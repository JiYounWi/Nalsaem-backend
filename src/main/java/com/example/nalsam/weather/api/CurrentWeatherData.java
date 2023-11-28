package com.example.nalsam.weather.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
public class CurrentWeatherData {
    @Getter
    @Setter
    @ToString
    public static class GetCurrentWeatherResponse{
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
//        private String dataType;
        private Items items;
//        private String pageNo;
//        private Integer numOfRows;
//        private Integer totalCount;
    }

    @Getter
    @Setter
    @ToString
    public static class Header{
        private String resultCode;
        private String resultMsg;
    }

    @ToString
    @Getter
    @Setter
    public static class Items{
        private List<Item> item;
    }

    @Getter
    @Setter
    @ToString
    public static class Item{
//        private String baseDate;
//        private String baseTime;
        private String category;
//        private String nx;
//        private String ny;
        private String obsrValue;
    }
}
