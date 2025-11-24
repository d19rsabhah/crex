package com.example.crex.dto.response;

import com.example.crex.model.enums.MatchStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CricketMatchResponse {
    int matchId;
    String venue;
    LocalDateTime matchDate;
    String tossWinner;
    String tossDecision;
    MatchStatus status;
    String result;
    List<String> teamNames;
    String category;   // "TOURNAMENT" or "SERIES"
    String categoryTitle;
}
