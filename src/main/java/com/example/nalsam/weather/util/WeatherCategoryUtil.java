package com.example.nalsam.weather.util;

import static com.example.nalsam.weather.util.WeatherCategory.*;

public class WeatherCategoryUtil {
    private WeatherCategoryUtil(){

    }
    public static WeatherCategory getCategory(String category){
        if(category.equals("T1H")){
            return 기온;
        }
        if(category.equals("RN1")){
            return 한시간강수량;
        }
        if(category.equals("REH")){
            return 습도;
        }
        if(category.equals("PTY")){
            return 강수형태;
        }
        if(category.equals("WSD")){
            return 풍속;
        }
        if(category.equals("VEC")){
            return 풍향;
        }
        if(category.equals("SKY")){
            return 하늘상태;
        }
        if(category.equals("TMX")){
            return 일최고기온;
        }
        if(category.equals("TMN")){
            return 일최저기온;
        }
        return 없음;
    }
    public static String getSkyValue(String value){
            if(value.equals("1")){
                return "맑음";
            }
            if(value.equals("3")){
                return "구름많음";
            }
            if(value.equals("4")){
                return "흐림";
            }
            return null;
    }
    public static String getRainValue(String value){
        if(value.equals("0")){
            return "강수없음";
        }
        if(value.equals("1")){
            return "비";
        }
        if(value.equals("2")){
            return "눈,비";
        }
        if(value.equals("3")){
            return "눈";
        }
        if(value.equals("4")){
            return "소나기";
        }
        return null;
    }





}
