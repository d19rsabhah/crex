package com.example.crex.repository;

import com.example.crex.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    List<Player> findByPlayerNameContainingIgnoreCase(String playerName);

}
