package com.example.nalsam.convergence.service;

public class HealthScore {

    private Integer heartRate; //심박수

    private Integer oxygenSaturation; // 산소포화도

    private Integer score;

    public HealthScore(Integer heartRate, Integer oxygenSaturation) {
        this.heartRate = heartRate;
        this.oxygenSaturation = oxygenSaturation;
        this.score = 0;
    }

    public int measureHealthScore() {
        measureHeartRateScore();
        measureOxygenSaturationScore();
        System.out.println("건강점수 : "+ score);
        return score;
    }

    private void measureHeartRateScore() {
        if (heartRate > 100) {
            score += 3;
        }
        if (heartRate >= 60 && heartRate <= 100) {
            score += 5;
        }
    }

    private void measureOxygenSaturationScore() {
        if (oxygenSaturation > 95) {
            score += 15;
        }
        if (oxygenSaturation >= 90 && oxygenSaturation < 95) {
            score += 5;
        }
    }

    public static void main(String[] args) {
        HealthScore healthScore = new HealthScore(95, 90);
        int score = healthScore.measureHealthScore();
        System.out.println("건강 점수 : " + score);
    }
}
