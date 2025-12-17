package com.example.crex.controller;

import com.example.crex.dto.request.CricketMatchRequest;
import com.example.crex.service.signature.CricketMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/matches")
@RequiredArgsConstructor
public class CricketMatchController {

    private final CricketMatchService matchService;

    // ADD MATCH (USER + ADMIN)
    @PostMapping("/add")
    public ResponseEntity<?> addMatch(
            @RequestBody CricketMatchRequest request,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(matchService.addMatch(request, token));
    }

    // GET ALL MATCHES (PUBLIC)
    @GetMapping("/all")
    public ResponseEntity<?> getAllMatches() {
        return ResponseEntity.ok(matchService.getAllMatches());
    }

    // GET MATCH BY ID (USER + ADMIN)
    @GetMapping("/{id}")
    public ResponseEntity<?> getMatchById(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(matchService.getMatchById(id, token));
    }

    // GET MATCHES BY SERIES ID (USER + ADMIN)
    @GetMapping("/series/{seriesId}")
    public ResponseEntity<?> getMatchesBySeriesId(
            @PathVariable Integer seriesId,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(matchService.getMatchesBySeriesId(seriesId, token));
    }

    // GET MATCHES BY SERIES TITLE (PUBLIC)
    @GetMapping("/series/name/{title}")
    public ResponseEntity<?> getMatchesBySeriesTitle(@PathVariable String title) {
        return ResponseEntity.ok(matchService.getMatchesBySeriesTitle(title));
    }

    // UPDATE MATCH (USER + ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMatch(
            @PathVariable Integer id,
            @RequestBody CricketMatchRequest request,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(matchService.updateMatch(id, request, token));
    }

    // DELETE MATCH (ADMIN ONLY)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMatch(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        matchService.deleteMatch(id, token);
        return ResponseEntity.ok("Match deleted successfully");
    }
}
