package com.example.nalsam.convergence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nalsam.convergence.domain.Convergence;

public interface ConvergenceRepository extends JpaRepository<Convergence,Long> {
	Convergence findByLoginId(String loginId);
}