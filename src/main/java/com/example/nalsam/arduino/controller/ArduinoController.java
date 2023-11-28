package com.example.nalsam.arduino.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nalsam.arduino.domain.HealthData;
import com.example.nalsam.arduino.service.ArduinoService;
import com.example.nalsam.user.jwt.LoginService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/arduino")
public class ArduinoController {
	private final ArduinoService arduinoService;
	private final LoginService loginService;

	@RequestMapping("/healthdata")
    public void updateHealthData(@RequestBody HealthData healthData)
    {
   		arduinoService.updateHealthData(loginService.getUserResponse(healthData.getToken()).getLoginId(),
   										Integer.parseInt(healthData.getOxygensaturation()),
   										Integer.parseInt(healthData.getHeartrate()));
    }
}
