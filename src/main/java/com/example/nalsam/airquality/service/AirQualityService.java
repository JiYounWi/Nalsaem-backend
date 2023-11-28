package com.example.nalsam.airquality.service;

import com.example.nalsam.airquality.domain.StationLocation;
import com.example.nalsam.airquality.dto.AirQualityInfo;
import com.example.nalsam.airquality.repository.StationLocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirQualityService {
    private final com.example.nalsam.airquality.api.AirQualityApiCaller AirQualityApiCaller;
    private final StationLocationService stationLocationService;

    @Cacheable(value = "airQualityCache", key = "#latitude + '_' + #longitude+'_'+#dateHourString")
    public AirQualityInfo getAirQualityInfo(Double latitude, Double longitude,String dateHourString ) {

        StationLocation nearestStation = findNearestStation(latitude, longitude);
        String sidoCode = nearestStation.getAddress().substring(0,2);
        var airQualityInfo = AirQualityApiCaller.getAir(sidoCode);
        if (nearestStation != null) {
            return airQualityInfo.searchByGu(nearestStation.getStationName());
        }
        return airQualityInfo;
    }
    public StationLocation findNearestStation(Double latitude, Double longitude){
        List<StationLocation> stationLocations = stationLocationService.getAllStationLocations();

        StationLocation nearestStation = null;
        double minDistance = Double.MAX_VALUE;


        for (StationLocation location : stationLocations) {
            double distance = calculateDistance(latitude, longitude, location.getLatitude(), location.getLongitude());
            if (distance < minDistance) {
                minDistance = distance;
                nearestStation = location;
            }
        }

        return nearestStation;
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
