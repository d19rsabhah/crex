package com.example.crex.converter;


import com.example.crex.dto.request.CricketMatchRequest;
import com.example.crex.dto.response.CricketMatchResponse;
import com.example.crex.model.entity.CricketMatch;
import com.example.crex.model.enums.MatchStatus;

public class CricketMatchConverter {
    public static CricketMatch cricketMatchRequestToCricketMatch(CricketMatchRequest cricketMatchRequest){
        return CricketMatch.builder()
                .matchTitle(cricketMatchRequest.getMatchTitle())
                .venue(cricketMatchRequest.getVenue())
                .matchDate(cricketMatchRequest.getMatchDate())
                .status(MatchStatus.SCHEDULED)
                .build();
    }

    public static CricketMatchResponse cricketMatchToCricketMatchResponse(CricketMatch cricketMatch){
        return CricketMatchResponse.builder()
                .matchId(cricketMatch.getMatchId())
                .matchTitle(cricketMatch.getMatchTitle())
                .venue(cricketMatch.getVenue())
                .matchDate(cricketMatch.getMatchDate())
                .team1(cricketMatch.getTeam1().getTeamName())
                .team2(cricketMatch.getTeam2().getTeamName())
                .status(cricketMatch.getStatus())
                .result(cricketMatch.getResult())
                .seriesTitle(
                        cricketMatch.getSeries() != null
                                ? cricketMatch.getSeries().getTitle()
                                : null
                )
                .tournamentTitle(
                        cricketMatch.getTournament() != null
                                ? cricketMatch.getTournament().getTitle()
                                : null
                )
                .build();
    }
}
