package com.example.crex.service.signature;

import com.example.crex.dto.request.PlayerRequest;
import com.example.crex.dto.request.UserRequest;
import com.example.crex.dto.response.PlayerResponse;
import com.example.crex.dto.response.UserResponse;

import java.util.List;

public interface PlayerService {

    PlayerResponse addPlayer(PlayerRequest playerRequest, Integer teamId, String token);

    PlayerResponse getPlayerById(Integer playerId, String token);

    PlayerResponse updatePlayer(Integer playerId, PlayerRequest request, String token);

    List<PlayerResponse> getPlayersByName(String name);
}
