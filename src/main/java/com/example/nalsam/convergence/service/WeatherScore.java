package com.example.nalsam.convergence.service;

public class WeatherScore {
    private int temperature; //기온
    private int precipitation; //강수량
    private int humidity;       //습도
    private int weatherScore;

    WeatherScore(Double temperature, int precipitation, int humidity) {
        this.temperature = convertDoubleToInt(temperature);
        this.precipitation = precipitation;
        this.humidity = humidity;
        this.weatherScore = 0;
    }

    public int convertDoubleToInt(double temperature) {
        return (int) Math.round(temperature);
    }

    public int measureWeatherScore() {
        weatherScore = measureTemperatuerScore() + measureHumidityScore() + measurePrecipitationScore();
        System.out.println("날씨 : "+weatherScore);
        return weatherScore;
    }

    public int measureTemperatuerScore() {
        int temperatureScore = 0;
        if (temperature >= 31) {
            temperatureScore += 3;
        }
        if (temperature >= 25 && temperature <= 30) {
            temperatureScore += 8;
        }
        if (temperature >= 10 && temperature < 25) {
            temperatureScore += 10;
        }
        if (temperature >= 0 && temperature < 10) {
            temperatureScore += 7;
        }
        if (temperature >= -5 && temperature <= 0) {
            temperatureScore += 5;
        }
        if (temperature < -5) {
            temperatureScore += 3;
        }
        return temperatureScore;
    }

    public int measureHumidityScore() {
        int humidityScore = 0;
        if (humidity >= 80) {
            humidity += 2;
        }
        if (humidity >= 30 && humidity < 80) {
            humidity += 5;
        }
        if (humidity < 30) {
            humidity += 2;
        }
        return humidityScore;
    }

    public int measurePrecipitationScore() {
        int precipitationScore = 0;
        if (precipitation == 0) {
            precipitationScore += 5;
        }
        if (precipitation > 0 && precipitation <= 5) {
            precipitationScore += 4;
        }
        if (precipitation > 5 && precipitation <= 10) {
            precipitationScore += 3;
        }
        if (precipitation > 10 && precipitation <= 15) {
            precipitationScore += 2;
        }
        if (precipitation > 15 && precipitation <= 30) {
            precipitationScore += 1;
        }
        return precipitationScore;
    }

}
