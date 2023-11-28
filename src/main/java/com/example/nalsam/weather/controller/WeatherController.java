package com.example.nalsam.weather.controller;

import com.example.nalsam.airquality.service.AirQualityService;


import com.example.nalsam.weather.domain.LocationInfo;
import com.example.nalsam.weather.dto.WeatherDto;
import com.example.nalsam.weather.dto.WeatherForecastDto;
import com.example.nalsam.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
//@RequestMapping("/weather")
public class WeatherController {
    private final AirQualityService airQualityService;
    private final WeatherService WeatherService;


    @GetMapping("/weather")
    public WeatherDto getCurrentWeatherInfo(@RequestParam(required = false) Double latitude,
                                            @RequestParam(required = false) Double longitude){
        if(latitude !=null && longitude !=null){
            LocationInfo nearestLocation = WeatherService.findNearestLocation(latitude,longitude);
            String nx = nearestLocation.getNx();
            String ny = nearestLocation.getNy();
            String sido = nearestLocation.getSido();
            String gu = nearestLocation.getGu();
            if(nearestLocation !=null){
                return WeatherService.getCurrentWeatherInfo(nx,ny,sido,gu);
            }
            else return null;

        }
        return WeatherService.getCurrentWeatherInfo(null,null,null,null);
    }
    @GetMapping("/weatherForecast")
    public WeatherForecastDto getWeatherForecast(@RequestParam(required = false) Double latitude,
                                                 @RequestParam(required = false) Double longitude){
        if(latitude !=null && longitude !=null){
            LocationInfo nearestLocation = WeatherService.findNearestLocation(latitude,longitude);
            String nx = nearestLocation.getNx();
            String ny = nearestLocation.getNy();
            String sido = nearestLocation.getSido();
            String gu = nearestLocation.getGu();
            if(nearestLocation !=null){
                return WeatherService.getWeatherForecastInfo(nx,ny,sido,gu);
            }
            else return null;

        }
        return WeatherService.getWeatherForecastInfo(null,null,null,null);
    }
}
