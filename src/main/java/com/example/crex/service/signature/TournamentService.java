package com.example.crex.service.signature;

import com.example.crex.dto.request.TournamentRequest;
import com.example.crex.dto.response.TournamentResponse;


import java.util.List;

public interface TournamentService {

    TournamentResponse createTournament(TournamentRequest request, String token);

    TournamentResponse updateTournament(int tournamentId, TournamentRequest request, String token);

    void deleteTournament(int tournamentId, String token);

    TournamentResponse getTournamentById(int tournamentId, String token);

    List<TournamentResponse> searchTournamentByTitle(String title);

    List<TournamentResponse> getAllTournaments();
}
