package com.example.nalsam.arduino.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_info")
public class Arduino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_id", nullable = false)
    private String loginId;
    
    @Column(name = "oxygen_saturation")
    private Integer oxygenSaturation;
    
    @Column(name = "heart_rate")
    private Integer heartRate;
    
    public void updateHealthData(Integer oxygenSaturation, Integer heartRate) {
        this.oxygenSaturation = oxygenSaturation;
        this.heartRate = heartRate;
    }
    
}
