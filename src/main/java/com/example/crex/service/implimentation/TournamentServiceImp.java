package com.example.crex.service.implimentation;

import com.example.crex.converter.TournamentConverter;
import com.example.crex.repository.TournamentRepository;
import com.example.crex.service.signature.TournamentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TournamentServiceImp implements TournamentService {
    final TournamentRepository tournamentRepository;
    final TournamentConverter tournamentConverter;


}
