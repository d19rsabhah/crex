package com.example.crex.repository;

import com.example.crex.model.entity.CricketMatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CricketMatchRepository extends JpaRepository<CricketMatch, Integer> {
}
