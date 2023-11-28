package com.example.nalsam.arduino.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.nalsam.arduino.repository.ArduinoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArduinoService {
	private final ArduinoRepository arduinoRepository;

    @Transactional
    public void updateHealthData(String LoginId, Integer oxygenSaturation, Integer heartrate){
    	arduinoRepository.findByLoginId(LoginId).updateHealthData(oxygenSaturation, heartrate);
    }
}