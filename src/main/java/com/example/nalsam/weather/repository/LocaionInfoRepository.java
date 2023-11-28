package com.example.nalsam.weather.repository;

import com.example.nalsam.weather.domain.LocationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocaionInfoRepository extends JpaRepository<LocationInfo,Long> {
}
