package com.example.crex.repository;

import com.example.crex.model.entity.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Integer> {
    boolean existsByToken(String token);
}

