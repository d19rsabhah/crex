package com.example.crex.converter;

import com.example.crex.dto.request.TournamentRequest;
import com.example.crex.dto.response.TournamentResponse;
import com.example.crex.model.entity.Tournament;

public class TournamentConverter {

    public static Tournament tournamentRequestToTournament(TournamentRequest tournamentRequest,
                                                           int createdBy){
        return Tournament.builder()
                .title(tournamentRequest.getTitle())
                .startDate(tournamentRequest.getStartDate())
                .endDate(tournamentRequest.getEndDate())
                .organizer(tournamentRequest.getOrganizer())
                .build();
    }

    public static TournamentResponse tournamentToTournamentResponse(Tournament tournament){
        int totalMatches = tournament.getMatches() == null
                ? 0
                : tournament.getMatches().size();

        return TournamentResponse.builder()
                .tournamentId(tournament.getTournamentId())
                .title(tournament.getTitle())
                .startDate(tournament.getStartDate())
                .endDate(tournament.getEndDate())
                .organizer(tournament.getOrganizer())
                .totalMatches(totalMatches)
                .build();
    }
}
