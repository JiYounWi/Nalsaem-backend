package com.example.nalsam.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.nalsam.user.domain.Users;

import java.util.Optional;


public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
}
