package com.example.crex.dto.request;

import java.time.LocalDate;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TournamentRequest {

    String title;
    LocalDate startDate;
    LocalDate endDate;
    String organizer;

}
