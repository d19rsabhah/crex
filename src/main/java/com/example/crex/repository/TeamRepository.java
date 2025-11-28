package com.example.crex.repository;

import com.example.crex.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    boolean existsByCountry(String country);

}
