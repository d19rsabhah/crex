package com.example.crex.dto.response;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeriesResponse {

    int seriesId;
    String title;
    String season;
    LocalDate startDate;
    LocalDate endDate;
    String organizer;
    int totalMatches;

}
