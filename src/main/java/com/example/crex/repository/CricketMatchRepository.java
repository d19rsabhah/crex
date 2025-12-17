package com.example.crex.repository;

import com.example.crex.model.entity.CricketMatch;
import com.example.crex.model.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CricketMatchRepository extends JpaRepository<CricketMatch, Integer> {
    List<CricketMatch> findBySeries(Series series);
}
