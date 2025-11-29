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

    // ------------------------------------------------------------------------------------
    // GET TEAM BY ID (USER + ADMIN)
    // GET: /api/v1/teams/{id}
    // ------------------------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> getTeamById(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        TeamResponse response = teamService.getTeamById(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ------------------------------------------------------------------------------------
    // SEARCH TEAM BY NAME (PUBLIC)
    // GET: /api/v1/teams/name/{name}
    // ------------------------------------------------------------------------------------
    @GetMapping("/search/name/{name}")
    public ResponseEntity<?> searchByName(@PathVariable String name) {

        TeamResponse response = teamService.searchByName(name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ------------------------------------------------------------------------------------
    // SEARCH TEAM BY COUNTRY (PUBLIC)
    // GET: /api/v1/teams/country/{country}
    // ------------------------------------------------------------------------------------
    @GetMapping("/search/country/{country}")
    public ResponseEntity<?> searchByCountry(@PathVariable String country) {

        TeamResponse response = teamService.searchByCountry(country);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTeam(
            @PathVariable Integer id,
            @RequestBody TeamRequest request,
            @RequestHeader("Authorization") String token) {

        TeamResponse response = teamService.updateTeam(id, request, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}


