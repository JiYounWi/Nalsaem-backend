package com.example.nalsam.convergence.domain;

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
public class Convergence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_id", nullable = false)
    private String loginId;
    
    @Column(name = "score")
    private Integer score;
    
    @Column(name = "convergence_explantion")
    private String convergenceExplantion;
    
    public void updateConvergence(Integer score, String convergenceExplantion) {
        this.score = score;
        this.convergenceExplantion = convergenceExplantion;
    }
    
}
