package com.example.nalsam.weather.api;

import com.example.nalsam.weather.dto.WeatherDto;
import com.example.nalsam.weather.dto.WeatherForecastDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class WeatherForecastApiCaller {
    private final WeatherForecastApi weatherForecastApi;

    @Value("${CURRENT-WEATHER-KEY}")
    private String SERVICE_KEY;

    public WeatherForecastApiCaller() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        this.weatherForecastApi = retrofit.create(WeatherForecastApi.class);
    }

    @Cacheable(value = "WeatherForecastCache", key = "#date + '_' + #time+ '_' + #nx+ '_' + #ny + #sido+ '_' + #gu")
    public WeatherForecastDto getWeatherForecast(String date , String time , String nx , String ny, String sido, String gu) {
        try {
            var call = weatherForecastApi.getWeatherForecast(SERVICE_KEY,"json",1,1000,date, time, nx, ny);               // Todo : nx,ny 작업
            var response = call.execute().body();

            if (response == null || response.getResponse() == null) {
                throw new RuntimeException(" Weather 응답값이 존재하지 않습니다.");
            }

            if (response.getResponse().isSuccess()) {
                log.info(response.toString());
                return convert(response,sido,gu);
            }

            throw new RuntimeException(" 날씨예보 응답이 올바르지 않습니다. header=" + response.getResponse().getHeader());

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(" 날씨예보 API error 발생! errorMessage=" + e.getMessage());
        }
    }

    private WeatherForecastDto convert(WeatherForecastData.GetWeatherForecastResponse response,String sido,String gu){

        var item = response.getResponse().getBody().getItems().getItem();

        var weaherList = convert(item);
        return WeatherForecastDto.builder()
                .sido(sido)
                .gu(gu)
                .weatherForecastInfos(weaherList)
                .build();

    }

    private List<WeatherForecastDto.WeatherForecastInfo> convert(List<WeatherForecastData.Item> items) {
        return items.stream()
                .filter(item-> (item.getCategory().equals("TMX")  || item.getCategory().equals("TMN")
                        || item.getCategory().equals("SKY") || item.getCategory().equals("PTY")) && (item.getFcstTime().equals("0600")
                        || item.getFcstTime().equals("1500")))
                .map(item -> new WeatherForecastDto.WeatherForecastInfo(
                        item.getFcstDate(),
                        item.getFcstTime(),
                        item.getCategory(),
                        item.getFcstValue())
                )
                .collect(Collectors.toList());
    }
}
