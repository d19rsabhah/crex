package com.example.crex.service.signature;

import com.example.crex.model.entity.Tournament;

import java.util.List;

public interface TournamentService {
    Tournament createTournament(Tournament tournament);

    Tournament updateTournament(int tournamentId, Tournament tournament);

    void deleteTournament(int tournamentId);

    Tournament getTournamentById(int tournamentId);

    List<Tournament> searchTournamentByTitle(String title);

    List<Tournament> getAllTournaments();
}
