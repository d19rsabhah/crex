package com.example.crex.controller;

import com.example.crex.dto.request.TeamRequest;
import com.example.crex.dto.response.TeamResponse;
import com.example.crex.service.signature.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;



    @PostMapping("/add")
    public ResponseEntity<?> addTeam(
            @RequestBody TeamRequest request,
            @RequestHeader("Authorization") String token) {
        System.out.println("🔥 CONTROLLER EXECUTED");


        TeamResponse response = teamService.addTeam(request, token);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}


