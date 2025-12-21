package com.example.crex.dto.response;

import java.time.LocalDate;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TournamentResponse {
    int tournamentId;
    String title;
    LocalDate startDate;
    LocalDate endDate;
    String organizer;
    int totalMatches;

}
