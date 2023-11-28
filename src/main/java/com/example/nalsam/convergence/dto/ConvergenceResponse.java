package com.example.nalsam.convergence.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConvergenceResponse {

    private Integer score;
    private String convergenceExplantion;
    // Todo : 사용자명 , Score,
    //  문자열 :질환 , 산소포화도 , 심박수 , 외출적합도

    public ConvergenceResponse(Integer score,String convergenceExplantion){
        this.score = score;
        this.convergenceExplantion =convergenceExplantion;
    }
}
