package com.example.nalsam.weather.api;

import com.example.nalsam.weather.dto.WeatherDto;
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
public class CurrentWeatherApiCaller {
    private final CurrentWeatherApi CurrentWeatherApi;

    @Value("${CURRENT-WEATHER-KEY}")
    private String SERVICE_KEY;

    public CurrentWeatherApiCaller() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        this.CurrentWeatherApi = retrofit.create(CurrentWeatherApi.class);
    }

    @Cacheable(value = "currentWeatherCache", key = "#date + '_' + #time+ '_' + #nx+ '_' + #ny+ #sido+ '_' + #gu")
    public WeatherDto getCurrentWeather(String date , String time , String nx , String ny,String sido, String gu) {
        try {
            var call = CurrentWeatherApi.getCurrentWeather(SERVICE_KEY,"json",1,100,date, time, nx, ny);               // Todo : nx,ny 작업
            var response = call.execute().body();

            if (response == null || response.getResponse() == null) {
                throw new RuntimeException(" Weather 응답값이 존재하지 않습니다.");
            }

            if (response.getResponse().isSuccess()) {
                log.info(response.toString());
                return convert(response,sido,gu);
            }

            throw new RuntimeException(" Weather 응답이 올바르지 않습니다. header=" + response.getResponse().getHeader());

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(" Weather API error 발생! errorMessage=" + e.getMessage());
        }
    }

    private WeatherDto convert(CurrentWeatherData.GetCurrentWeatherResponse response,String sido,String gu){

        var item = response.getResponse().getBody().getItems().getItem();

        var weaherList = convert(item);
        return WeatherDto.builder()
                .sido(sido)
                .gu(gu)
                .weatherInfoList(weaherList)
                .build();

    }

    private List<WeatherDto.WeatherInfo> convert(List<CurrentWeatherData.Item> items) {
        return items.stream()
                .filter(item-> item.getCategory().equals("T1H")  || item.getCategory().equals("REH")
                        || item.getCategory().equals("PTY") || item.getCategory().equals("RN1")
                        || item.getCategory().equals("WSD")|| item.getCategory().equals("VEC"))
                .map(item -> new WeatherDto.WeatherInfo(
                        item.getCategory(),
                        item.getObsrValue())
                )
                .collect(Collectors.toList());
    }
}
