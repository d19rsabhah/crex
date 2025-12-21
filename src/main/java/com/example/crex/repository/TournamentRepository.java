package com.example.crex.repository;

import com.example.crex.model.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TournamentRepository extends JpaRepository<Tournament, Integer> {
    List<Tournament> findByTitleContainingIgnoreCase(String title);
}
