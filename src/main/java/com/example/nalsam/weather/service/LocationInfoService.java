package com.example.nalsam.weather.service;


import com.example.nalsam.weather.domain.LocationInfo;
import com.example.nalsam.weather.repository.LocaionInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationInfoService {
    private final LocaionInfoRepository locationInfoRepository;

    @Autowired
    public LocationInfoService(LocaionInfoRepository locationInfoRepository){
        this.locationInfoRepository = locationInfoRepository;
    }
    public List<LocationInfo> getAllLocationInfo(){
        return locationInfoRepository.findAll();
    }

}
