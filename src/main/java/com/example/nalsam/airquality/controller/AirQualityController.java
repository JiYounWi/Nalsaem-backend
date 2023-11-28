package com.example.nalsam.airquality.controller;

import com.example.nalsam.airquality.domain.StationLocation;
import com.example.nalsam.airquality.dto.AirQualityInfo;
import com.example.nalsam.airquality.service.AirQualityService;
import com.example.nalsam.airquality.util.DateUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/air")
public class AirQualityController {
    private final AirQualityService airQualityService;
    @GetMapping("")
    public AirQualityInfo getAirQualityInfo(@RequestParam(required = false) Double latitude,
                                            @RequestParam(required = false) Double longitude) {
        if (latitude != null && longitude != null) {
            StationLocation nearestStation = airQualityService.findNearestStation(latitude, longitude);
            if (nearestStation != null) {
                return airQualityService.getAirQualityInfo(nearestStation.getLatitude(), nearestStation.getLongitude(), DateUtil.getDateHourString());
            } else {
                return null;
            }
        } else {
            return airQualityService.getAirQualityInfo(null, null,null);
        }
    }
}