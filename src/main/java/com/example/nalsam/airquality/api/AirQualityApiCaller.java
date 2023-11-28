package com.example.nalsam.airquality.api;

import com.example.nalsam.airquality.dto.AirQualityInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AirQualityApiCaller {
    private final AirQualityApi AirQualityApi;

    @Value("${AIR-QUALITY-KEY}")
    private String SERVICE_KEY;

    public AirQualityApiCaller() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        this.AirQualityApi = retrofit.create(AirQualityApi.class);
    }


    public AirQualityInfo getAir(String sidoCode){
        try {
            var call = AirQualityApi.getAirQuality(SERVICE_KEY, "json", 700, 1, "1.0", sidoCode);
            var response = call.execute().body();

            if (response == null || response.getResponse() == null) {
                throw new RuntimeException(" getAirQuality 응답값이 존재하지 않습니다.");
            }

            if (response.getResponse().isSuccess()) {
                log.info(response.toString());
                return convert(response,sidoCode);
            }

            throw new RuntimeException(" getAirQuality 응답이 올바르지 않습니다. header=" + response.getResponse().getHeader());

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(" getAirQuality API error 발생! errorMessage=" + e.getMessage());
        }
    }
    private AirQualityInfo convert(AirQualityData.GetAirQualityResponse response, String sidoCode){
        var items = response.getResponse().getBody().getItems();
        var guList = convert(items);

        return AirQualityInfo.builder()
                .sidoCode(sidoCode)
                .guList(guList)
                .build();
    }

    private List<AirQualityInfo.GuAirQualityInfo> convert(List<AirQualityData.Item> items) {
        return items.stream()
                .map(item -> new AirQualityInfo.GuAirQualityInfo(
                        item.getStationName(),
                        replaceDefaultValue(item.getSo2Value()),
                        replaceDefaultValue(item.getCoValue()),
                        replaceDefaultValue(item.getPm10Value()),
                        replaceDefaultValue(item.getPm25Value()),
                        replaceDefaultValue(item.getNo2Value()),
                        replaceDefaultValue(item.getO3Value()))
                )
                .collect(Collectors.toList());
    }

    private String replaceDefaultValue(String value) {
        if(value == null || value.equals("-")){
            return "0";
        }
        return value;
    }

}
