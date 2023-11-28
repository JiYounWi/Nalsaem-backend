package com.example.nalsam.weather.dto;

import com.example.nalsam.weather.util.WeatherCategory;
import com.example.nalsam.weather.util.WeatherCategoryUtil;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder
public class WeatherForecastDto {
    private String sido;
    private String gu;
    private List<WeatherForecastInfo> weatherForecastInfos;

    @Getter
    public static class WeatherForecastInfo{
        private String date;
        private String time;
        private WeatherCategory category;
        private String value;

        public WeatherForecastInfo(String date,String time,String category,String value){
            this.date=date;
            this.time=time;
            this.category = WeatherCategoryUtil.getCategory(category);
            if(category.equals("SKY")){
                this.value= WeatherCategoryUtil.getSkyValue(value);
            }
            else if(category.equals("PTY")){
                this.value = WeatherCategoryUtil.getRainValue(value);
            }
            else{
                this.value = value;
            }
        }

    }
}
