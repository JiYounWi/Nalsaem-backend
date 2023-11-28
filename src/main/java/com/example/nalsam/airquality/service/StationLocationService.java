package com.example.nalsam.airquality.service;

import com.example.nalsam.airquality.domain.StationLocation;
import com.example.nalsam.airquality.repository.StationLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationLocationService {
    private final StationLocationRepository stationLocationRepository;
    @Autowired
    public StationLocationService(StationLocationRepository stationLocationRepository) {
        this.stationLocationRepository = stationLocationRepository;
    }
    public List<StationLocation> getAllStationLocations() {
        return stationLocationRepository.findAll();
    }
}
