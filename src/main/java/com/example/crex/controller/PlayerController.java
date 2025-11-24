package com.example.crex.controller;

import com.example.crex.dto.request.PlayerRequest;
import com.example.crex.dto.response.PlayerResponse;
import com.example.crex.service.signature.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth/user/")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @PostMapping("/add-player")
    public ResponseEntity<?> addPlayer(
            @RequestBody PlayerRequest playerRequest,
            @RequestParam("team-id") Integer teamId,
            @RequestHeader("Authorization") String token) {

        PlayerResponse response = playerService.addPlayer(playerRequest, teamId, token);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
