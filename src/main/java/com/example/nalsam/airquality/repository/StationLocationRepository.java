package com.example.nalsam.airquality.repository;

import com.example.nalsam.airquality.domain.StationLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationLocationRepository extends JpaRepository<StationLocation,Long> {
}
