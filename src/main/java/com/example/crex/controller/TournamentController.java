package com.example.crex.controller;

import com.example.crex.dto.request.TournamentRequest;
import com.example.crex.service.signature.TournamentService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tournaments")
@RequiredArgsConstructor

public class TournamentController {

    private final TournamentService tournamentService;

    // CREATE (USER + ADMIN)
    @PostMapping
    public ResponseEntity<?> createTournament(
            @RequestBody TournamentRequest request,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tournamentService.createTournament(request, token));
    }

    // UPDATE (USER + ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTournament(
            @PathVariable int id,
            @RequestBody TournamentRequest request,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                tournamentService.updateTournament(id, request, token)
        );
    }

    // DELETE (ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTournament(
            @PathVariable int id,
            @RequestHeader("Authorization") String token) {

        tournamentService.deleteTournament(id, token);
        return ResponseEntity.ok("Tournament deleted successfully");
    }

    // GET BY ID (USER + ADMIN)
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable int id,
            @RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(
                tournamentService.getTournamentById(id, token)
        );
    }

    // SEARCH BY TITLE (PUBLIC)
    @GetMapping("/search")
    public ResponseEntity<?> searchByTitle(@RequestParam String title) {

        return ResponseEntity.ok(
                tournamentService.searchTournamentByTitle(title)
        );
    }

    // GET ALL (PUBLIC)
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {

        return ResponseEntity.ok(
                tournamentService.getAllTournaments()
        );
    }
}
