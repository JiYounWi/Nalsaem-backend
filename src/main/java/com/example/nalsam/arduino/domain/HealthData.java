package com.example.nalsam.arduino.domain;

import lombok.Data;

@Data
public class HealthData {
	private String oxygensaturation;
	private String heartrate;
	private String token;
}
