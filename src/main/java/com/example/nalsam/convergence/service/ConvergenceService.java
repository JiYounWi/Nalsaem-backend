package com.example.nalsam.convergence.service;

import java.time.LocalDate;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.nalsam.convergence.dto.ConvergenceData;
import com.example.nalsam.convergence.dto.ConvergenceRequest;
import com.example.nalsam.convergence.dto.ConvergenceResponse;
import com.example.nalsam.convergence.repository.ConvergenceRepository;
import com.example.nalsam.user.domain.Users;
import com.example.nalsam.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConvergenceService {

    private final UserService userService;
    private final ConvergenceRepository	convergenceRepository;
    private static final String defaultExplanation =
            "현재 %s님의 %s 상태를 고려하여, 산소포화도 %d%% 심박수 %d BPM, 대기질, 날씨를 융합한 외출상태 점수는 %s점 입니다. 이는 %s 상태를 뜻합니다.";


    public ConvergenceData colletConvergenceData(ConvergenceRequest convergenceRequest) {
        String loginId = convergenceRequest.getLoginId();
        Users user = userService.findUserByLoginId(loginId);

        int age = calculateAge(user.getBirthDate());

        return ConvergenceData.builder()
                .pm10Grade(convergenceRequest.getPm10Grade())
                .pm25Grade(convergenceRequest.getPm25Grade())
                .so2Grade(convergenceRequest.getSo2Grade())
                .o3Grade(convergenceRequest.getO3Grade())
                .no2Grade(convergenceRequest.getNo2Grade())
                .coGrade(convergenceRequest.getCoGrade())
                .temperature(convergenceRequest.getTemperature())
                .precipitation(convergenceRequest.getPrecipitation())
                .humidity(convergenceRequest.getHumidity())
                .userName(user.getName())
                .age(age)
                .heartRate(user.getHeartRate())
                .oxygenSaturation(user.getOxygenSaturation())
                .symptom(user.getSymptom())
                .build();
    }

    private int calculateAge(String birthDate) {
        Integer userBirthYear = Integer.parseInt(birthDate.substring(0, 4));
        Integer nowYear = LocalDate.now().getYear();

        return nowYear - userBirthYear;
    }

    public ConvergenceResponse measureConvergenceScore(ConvergenceData convergenceData) {
        int convergenceScore = convergenceScore(convergenceData);
        String convergenceExplaintoin = convergenceExplaintoin(convergenceScore, convergenceData);
        return new ConvergenceResponse(convergenceScore, convergenceExplaintoin);
    }

    private int convergenceScore(ConvergenceData convergenceData) {
        int convergenceScore = Stream.of(
                        new StatusScore(convergenceData.getAge(), convergenceData.getSymptom()).measureStatusScore(),
                        new HealthScore(convergenceData.getHeartRate(),
                                convergenceData.getOxygenSaturation()).measureHealthScore(),
                        new WeatherScore(convergenceData.getTemperature(), convergenceData.getHumidity(),
                                convergenceData.getPrecipitation()).measureWeatherScore(),
                        new AirQualityScore(convergenceData.getPm10Grade(), convergenceData.getPm25Grade(),
                                convergenceData.getSo2Grade(),
                                convergenceData.getO3Grade(), convergenceData.getNo2Grade(), convergenceData.getCoGrade(),
                                convergenceData.getSymptom()).measureAirQualityScore())
                .reduce(0, Integer::sum);
        return convergenceScore;
    }

    private String convergenceExplaintoin(int convergenceScore, ConvergenceData convergenceData) {
        String goingOutMessage = generateGoingOutMessage(convergenceScore);
        String formattedMessage = String.format(defaultExplanation, convergenceData.getUserName(),
                convergenceData.getSymptom(), convergenceData.getOxygenSaturation(), convergenceData.getHeartRate(),
                convergenceScore, goingOutMessage);
        return formattedMessage;
    }

    private String generateGoingOutMessage(int convergenceScore) {
        if (convergenceScore >= 0 && convergenceScore <= 45) {
            return "외출 부적합";
        }
        if (convergenceScore >= 46 && convergenceScore <= 55) {
            return "외출자제 요망";
        }
        if (convergenceScore >= 56 && convergenceScore <= 70) {
            return "외출자체 판단";
        }
        return "외출 적합";
    }
    
    @Transactional
    public void updateConvergence(String LoginId, Integer score, String convergenceExplantion){
    	convergenceRepository.findByLoginId(LoginId).updateConvergence(score, convergenceExplantion);
    }

}
