package com.example.crex.dto.request;

import com.example.crex.model.enums.MatchStatus;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CricketMatchRequest {
    String matchTitle;
    String venue;
    LocalDateTime matchDate;

    int team1Id;
    int team2Id;

    // Exactly ONE of these must be present
    int seriesId;        // optional
    Integer tournamentId;    // optional
}
