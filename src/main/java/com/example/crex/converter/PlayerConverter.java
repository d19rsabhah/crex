package com.example.crex.converter;

import com.example.crex.dto.request.PlayerRequest;
import com.example.crex.dto.response.PlayerResponse;
import com.example.crex.dto.response.TeamResponse;
import com.example.crex.model.entity.Player;

import java.time.LocalDate;
import java.time.Period;

public class PlayerConverter {

    public static Player playerRequestToPlayer(PlayerRequest playerRequest){
        return Player.builder()
                .playerName(playerRequest.getPlayerName())
                .dob(playerRequest.getDob())
                .email(playerRequest.getEmail())
                .gender(playerRequest.getGender())
                .height(playerRequest.getHeight())
                .weight(playerRequest.getWeight())
                .role(playerRequest.getRole())
                .jerseyNumber(playerRequest.getJerseyNumber())
                .battingStyle(playerRequest.getBattingStyle())
                .bowlingStyle(playerRequest.getBowlingStyle())
                .nationality(playerRequest.getNationality())
                .placeOfBirth(playerRequest.getPlaceOfBirth())
                .build();
    }
    public static PlayerResponse playerToPlayerResponse(Player player){
        int age = Period.between(player.getDob(), LocalDate.now()).getYears();

        String teamName = (player.getTeam() != null)
                ? player.getTeam().getTeamName()
                : null;

        return PlayerResponse.builder()
                .playerId(player.getPlayerId())
                .playerName(player.getPlayerName())
                .email(player.getEmail())
                .gender(player.getGender())
                .role(player.getRole())
                .jerseyNumber(player.getJerseyNumber())
                .nationality(player.getNationality())
                .teamName(teamName)
                .build();
    }

    public static int calculateAge(LocalDate dob) {
        if (dob == null) return 0;
        return Period.between(dob, LocalDate.now()).getYears();
    }

}
