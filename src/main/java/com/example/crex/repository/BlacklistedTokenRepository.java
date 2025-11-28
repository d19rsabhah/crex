package com.example.crex.repository;

import com.example.crex.model.entity.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {
    boolean existsByToken(String token);
    Optional<BlacklistedToken> findByToken(String token);
}


