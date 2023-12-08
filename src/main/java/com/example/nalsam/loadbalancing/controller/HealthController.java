package com.example.nalsam.loadbalancing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/")
    public ResponseEntity<HttpStatus> healthCheck(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated();
    }
}
