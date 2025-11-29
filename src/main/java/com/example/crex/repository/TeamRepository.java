package com.example.crex.repository;

import com.example.crex.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    boolean existsByCountry(String country);
    Optional<Team> findByTeamNameIgnoreCase(String teamName);

    Optional<Team> findByCountryIgnoreCase(String country);

}
