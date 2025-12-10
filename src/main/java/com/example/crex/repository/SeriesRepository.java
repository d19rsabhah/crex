package com.example.crex.repository;

import com.example.crex.model.entity.Series;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Integer> {
    boolean existsByTitleIgnoreCase(String title);
}
