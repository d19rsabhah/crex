package com.example.crex.dto.request;

import com.example.crex.model.enums.MatchStatus;

import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CricketMatchRequest {
    LocalDateTime matchDate;
    String venue;
    String tossWinner;        // optional
    String tossDecision;      // optional
    MatchStatus status;       // SCHEDULED / LIVE / COMPLETED
    Long tournamentId;        // nullable (match belongs to tournament OR series)
    Long seriesId;            // nullable
    List<Long> teamIds;
}
