package com.example.nalsam.weather.service;

import com.example.nalsam.weather.api.CurrentWeatherApiCaller;
import com.example.nalsam.weather.api.WeatherForecastApiCaller;
import com.example.nalsam.weather.domain.LocationInfo;
import com.example.nalsam.weather.dto.WeatherDto;
import com.example.nalsam.weather.dto.WeatherForecastDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {
    private final CurrentWeatherApiCaller currentWeatherApiCaller;
    private final WeatherForecastApiCaller weatherForecastApiCaller;
    private final LocationInfoService locationInfoService;

    public WeatherDto getCurrentWeatherInfo(String nx,String ny,String sido,String gu) {

        LocalDateTime dateTime = LocalDateTime.now();
        String date = String.valueOf(dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        Date today = new Date();
        SimpleDateFormat sdformat = new SimpleDateFormat("HH" );
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.HOUR, -1);
        String time="";
        time = sdformat.format(cal.getTime())+"00";

        System.out.println("1시간전 : " + time);
        System.out.println("date : "+date);

        var WeatherInfo = currentWeatherApiCaller.getCurrentWeather(date,time,nx,ny,sido,gu);
        return WeatherInfo;
    }
    public WeatherForecastDto getWeatherForecastInfo(String nx, String ny, String sido, String gu) {

        // 현재 날짜와 시간 가져오기
        LocalDateTime currentDateTime = LocalDateTime.now();


        // 출력 형식 지정
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HHmm");

        // 02시를 기준으로 분기
        LocalDateTime resultDateTime;
        if (currentDateTime.getHour() >= 2) {
            resultDateTime = currentDateTime.withHour(2).withMinute(0);
        } else {
            resultDateTime = currentDateTime.minusDays(1).withHour(23).withMinute(0);
        }
        System.out.println("resultDateTime  : "+ resultDateTime.getHour());
        System.out.println("---------------------");
        // date와 hour 변수로 분리
        String date = resultDateTime.format(dateFormatter);
        String hour = resultDateTime.format(hourFormatter);

        // 출력
        System.out.println("Date: " + date);
        System.out.println("Hour: " + hour);


        var WeatherInfo = weatherForecastApiCaller.getWeatherForecast(date,hour,nx,ny,sido,gu);
        return WeatherInfo;
    }
    public LocationInfo findNearestLocation(Double latitude,Double longitude){
        List<LocationInfo> locationInfos = locationInfoService.getAllLocationInfo();

        LocationInfo nearestLocation = null;
        double minDistance = Double.MAX_VALUE;
        for (LocationInfo location : locationInfos) {
            double distance = calculateDistance(latitude, longitude, location.getLatitude(), location.getLongitude());
            if (distance < minDistance) {
                minDistance = distance;
                nearestLocation = location;
            }
        }


        return nearestLocation;
    }
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = R * c;

        return distance;
    }
}
