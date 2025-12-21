package com.example.crex.service.implimentation;

import com.example.crex.config.JwtService;
import com.example.crex.converter.TournamentConverter;
import com.example.crex.dto.request.TournamentRequest;
import com.example.crex.dto.response.TournamentResponse;
import com.example.crex.exception.ResourceNotFoundException;
import com.example.crex.model.entity.Tournament;
import com.example.crex.model.entity.User;
import com.example.crex.repository.TournamentRepository;
import com.example.crex.repository.UserRepository;
import com.example.crex.service.signature.TournamentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
@RequiredArgsConstructor
class TournamentServiceImpl implements TournamentService {

    final TournamentRepository tournamentRepository;
    final JwtService jwtService;
    final UserRepository userRepository;

    // ========================
    // CREATE TOURNAMENT (USER + ADMIN)
    // ========================
    @Override
    public TournamentResponse createTournament(TournamentRequest request, String token) {

        if (token.startsWith("Bearer ")) token = token.substring(7);

        String email = jwtService.extractUsername(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Tournament tournament =
                TournamentConverter.tournamentRequestToTournament(request, user);

        Tournament saved = tournamentRepository.save(tournament);

        return TournamentConverter.tournamentToTournamentResponse(saved);
    }

    // ========================
    // UPDATE TOURNAMENT (USER + ADMIN)
    // ========================
    @Override
    public TournamentResponse updateTournament(int tournamentId,
                                               TournamentRequest request,
                                               String token) {

        if (token.startsWith("Bearer ")) token = token.substring(7);

        jwtService.extractUsername(token); // validation only

        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found"));

        if (request.getTitle() != null)
            tournament.setTitle(request.getTitle());
        if (request.getStartDate() != null)
            tournament.setStartDate(request.getStartDate());
        if (request.getEndDate() != null)
            tournament.setEndDate(request.getEndDate());
        if (request.getOrganizer() != null)
            tournament.setOrganizer(request.getOrganizer());

        Tournament updated = tournamentRepository.save(tournament);

        return TournamentConverter.tournamentToTournamentResponse(updated);
    }

    // ========================
    // DELETE TOURNAMENT (ADMIN ONLY)
    // ========================
    @Override
    public void deleteTournament(int tournamentId, String token) {

        if (token.startsWith("Bearer ")) token = token.substring(7);

        jwtService.extractUsername(token); // validated by security layer

        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found"));

        tournamentRepository.delete(tournament);
    }

    // ========================
    // GET BY ID (USER + ADMIN)
    // ========================
    @Override
    public TournamentResponse getTournamentById(int tournamentId, String token) {

        if (token.startsWith("Bearer ")) token = token.substring(7);

        jwtService.extractUsername(token);

        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found"));

        return TournamentConverter.tournamentToTournamentResponse(tournament);
    }

    // ========================
    // SEARCH BY TITLE (PUBLIC)
    // ========================
    @Override
    public List<TournamentResponse> searchTournamentByTitle(String title) {

        List<Tournament> tournaments =
                tournamentRepository.findByTitleContainingIgnoreCase(title);

        if (tournaments.isEmpty()) {
            throw new ResourceNotFoundException("No tournaments found");
        }

        return tournaments.stream()
                .map(TournamentConverter::tournamentToTournamentResponse)
                .toList();
    }

    // ========================
    // GET ALL (PUBLIC)
    // ========================
    @Override
    public List<TournamentResponse> getAllTournaments() {

        return tournamentRepository.findAll()
                .stream()
                .map(TournamentConverter::tournamentToTournamentResponse)
                .toList();
    }
}

