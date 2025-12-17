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
@Builder
public class CricketMatchResponse {
    int matchId;
    String matchTitle;
    String venue;
    LocalDateTime matchDate;

    String team1;
    String team2;
    MatchStatus status;
    String result;
    String seriesTitle;       // nullable
    String tournamentTitle;
}
