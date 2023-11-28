package com.example.nalsam.weather.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class LocationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_code")
    private Double locationCode;
    private String sido;
    private String gu;
    @Column(name = "point_x")
    private String nx;
    @Column(name = "point_y")
    private String ny;
    private Double latitude;
    private Double longitude;
}

