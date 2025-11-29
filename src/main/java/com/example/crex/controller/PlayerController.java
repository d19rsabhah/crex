package com.example.crex.controller;

import com.example.crex.dto.request.PlayerRequest;
import com.example.crex.dto.response.PlayerResponse;
import com.example.crex.service.implimentation.PlayerServiceImp;
import com.example.crex.service.signature.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping("/add")
    public ResponseEntity<?> addPlayer(
            @RequestBody PlayerRequest playerRequest,
            @RequestParam("team-id") Integer teamId,
            @RequestHeader("Authorization") String token) {

        System.out.println("🔥 PLAYER CONTROLLER EXECUTED");

        PlayerResponse response = playerService.addPlayer(playerRequest, teamId, token);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<?> getPlayerById(
            @PathVariable Integer playerId,
            @RequestHeader("Authorization") String token) {

        System.out.println("🔥 GET PLAYER CONTROLLER EXECUTED");

        PlayerResponse response = playerService.getPlayerById(playerId, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/update/{playerId}")
//    @PutMapping("/update/{playerId}")
    public ResponseEntity<?> updatePlayer(
            @PathVariable Integer playerId,
            @RequestBody PlayerRequest request,
            @RequestHeader("Authorization") String token) {

        System.out.println("🔥 UPDATE PLAYER CONTROLLER EXECUTED");

        PlayerResponse response = playerService.updatePlayer(playerId, request, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
