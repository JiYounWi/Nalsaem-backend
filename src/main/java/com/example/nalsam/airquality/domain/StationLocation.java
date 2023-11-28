package com.example.nalsam.airquality.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class StationLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_name")
    private String stationName;
    private Double latitude;
    private Double longitude;
    private String address;

}
