package com.example.crex.service.signature;

import com.example.crex.dto.request.CricketMatchRequest;
import com.example.crex.dto.response.CricketMatchResponse;

import java.util.List;

public interface CricketMatchService {

    CricketMatchResponse addMatch(CricketMatchRequest cricketMatchRequest, String token);

    List<CricketMatchResponse> getAllMatches();

    CricketMatchResponse getMatchById(int matchId, String token);

    List<CricketMatchResponse> getMatchesBySeriesId(int seriesId, String token);

    List<CricketMatchResponse> getMatchesBySeriesTitle(String title);

    CricketMatchResponse updateMatch(int matchId, CricketMatchRequest request, String token);

    void deleteMatch(int matchId, String token);
}
