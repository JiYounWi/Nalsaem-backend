package com.example.nalsam.weather.dto;

import com.example.nalsam.weather.util.WeatherCategory;
import com.example.nalsam.weather.util.WeatherCategoryUtil;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class WeatherDto {
    private String sido;
    private String gu;
    private List<WeatherInfo> weatherInfoList;

    @Getter
    public static class WeatherInfo{
        private WeatherCategory category;
        private String value;

        public WeatherInfo(String category,String value){
            this.category = WeatherCategoryUtil.getCategory(category);
            if(category.equals("PTY")){
                this.value = WeatherCategoryUtil.getRainValue(value);
            }
            else {
                this.value = value;
            }
        }
    }
}