package com.example.nalsam.arduino.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nalsam.arduino.domain.Arduino;

public interface ArduinoRepository extends JpaRepository<Arduino,Long> {
	Arduino findByLoginId(String loginId);
}
