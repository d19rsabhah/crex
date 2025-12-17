package com.example.crex.service.implimentation;

import com.example.crex.config.JwtService;
import com.example.crex.converter.CricketMatchConverter;
import com.example.crex.dto.request.CricketMatchRequest;
import com.example.crex.dto.response.CricketMatchResponse;
import com.example.crex.exception.ResourceNotFoundException;
import com.example.crex.model.entity.CricketMatch;
import com.example.crex.model.entity.Series;
import com.example.crex.model.entity.Team;
import com.example.crex.model.entity.Tournament;
import com.example.crex.model.enums.MatchStatus;
import com.example.crex.repository.*;
import com.example.crex.service.signature.CricketMatchService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
@RequiredArgsConstructor
public class CricketMatchServiceImp implements CricketMatchService {
    final CricketMatchRepository matchRepository;
    final TeamRepository teamRepository;
    final SeriesRepository seriesRepository;
    final TournamentRepository tournamentRepository;
    final JwtService jwtService;
    final UserRepository userRepository;

    @Override
    public CricketMatchResponse addMatch(CricketMatchRequest request, String token) {

        // 🔐 Validate token
        String email = extractEmailFromToken(token);
        userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // 🏏 Validate teams
        if (request.getTeam1Id() == request.getTeam2Id()) {
            throw new RuntimeException("Team1 and Team2 cannot be same");
        }

        Team team1 = teamRepository.findById(request.getTeam1Id())
                .orElseThrow(() -> new ResourceNotFoundException("Team 1 not found"));

        Team team2 = teamRepository.findById(request.getTeam2Id())
                .orElseThrow(() -> new ResourceNotFoundException("Team 2 not found"));

        // 🔁 Validate Series / Tournament (EXACTLY ONE)
        boolean hasSeries = request.getSeriesId() > 0;
        boolean hasTournament = request.getTournamentId() != null;

        if (hasSeries == hasTournament) {
            throw new RuntimeException("Match must belong to either Series OR Tournament (not both)");
        }

        // 🧩 Convert basic fields
        CricketMatch match = CricketMatchConverter
                .cricketMatchRequestToCricketMatch(request);

        match.setTeam1(team1);
        match.setTeam2(team2);

        if (hasSeries) {
            Series series = seriesRepository.findById(request.getSeriesId())
                    .orElseThrow(() -> new ResourceNotFoundException("Series not found"));
            match.setSeries(series);
        }

        if (hasTournament) {
            Tournament tournament = tournamentRepository.findById(request.getTournamentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tournament not found"));
            match.setTournament(tournament);
        }

        CricketMatch savedMatch = matchRepository.save(match);

        return CricketMatchConverter
                .cricketMatchToCricketMatchResponse(savedMatch);
    }

    // =====================================================
    // GET ALL MATCHES (PUBLIC)
    // =====================================================
    @Override
    public List<CricketMatchResponse> getAllMatches() {

        return matchRepository.findAll()
                .stream()
                .map(CricketMatchConverter::cricketMatchToCricketMatchResponse)
                .toList();
    }

    // =====================================================
    // GET MATCH BY ID (USER + ADMIN)
    // =====================================================
    @Override
    public CricketMatchResponse getMatchById(int matchId, String token) {

        extractEmailFromToken(token); // just validate token

        CricketMatch match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found"));

        return CricketMatchConverter
                .cricketMatchToCricketMatchResponse(match);
    }

    // =====================================================
    // GET MATCHES BY SERIES ID (USER + ADMIN)
    // =====================================================
    @Override
    public List<CricketMatchResponse> getMatchesBySeriesId(int seriesId, String token) {

        extractEmailFromToken(token);

        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new ResourceNotFoundException("Series not found"));

        List<CricketMatch> matches = matchRepository.findBySeries(series);

        if (matches.isEmpty()) {
            throw new ResourceNotFoundException("No matches found for this series");
        }

        return matches.stream()
                .map(CricketMatchConverter::cricketMatchToCricketMatchResponse)
                .toList();
    }

    // =====================================================
    // GET MATCHES BY SERIES TITLE (PUBLIC)
    // =====================================================
    @Override
    public List<CricketMatchResponse> getMatchesBySeriesTitle(String title) {

        Series series = seriesRepository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new ResourceNotFoundException("Series not found"));

        List<CricketMatch> matches = matchRepository.findBySeries(series);

        if (matches.isEmpty()) {
            throw new ResourceNotFoundException("No matches found for series: " + title);
        }

        return matches.stream()
                .map(CricketMatchConverter::cricketMatchToCricketMatchResponse)
                .toList();
    }

    // =====================================================
    // UPDATE MATCH (USER + ADMIN)
    // =====================================================
    @Override
    public CricketMatchResponse updateMatch(int matchId,
                                            CricketMatchRequest request,
                                            String token) {

        extractEmailFromToken(token);

        CricketMatch match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found"));

        // Partial updates
        if (request.getMatchTitle() != null)
            match.setMatchTitle(request.getMatchTitle());

        if (request.getVenue() != null)
            match.setVenue(request.getVenue());

        if (request.getMatchDate() != null)
            match.setMatchDate(request.getMatchDate());

        CricketMatch updated = matchRepository.save(match);

        return CricketMatchConverter
                .cricketMatchToCricketMatchResponse(updated);
    }

    // =====================================================
    // DELETE MATCH (ADMIN ONLY)
    // =====================================================
    @Override
    public void deleteMatch(int matchId, String token) {

        extractEmailFromToken(token);

        CricketMatch match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found"));

        matchRepository.delete(match);
    }

    // =====================================================
    // 🔐 TOKEN UTILITY
    // =====================================================
    private String extractEmailFromToken(String token) {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        token = token.trim();

        return jwtService.extractUsername(token);
    }


}
