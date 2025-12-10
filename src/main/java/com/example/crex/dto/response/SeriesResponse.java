package com.example.crex.dto.response;

import java.time.LocalDate;

import com.example.crex.model.enums.SeriesFormat;
import com.example.crex.model.enums.SeriesOrganizer;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SeriesResponse {

    int seriesId;
    String title;
    String season;
    LocalDate startDate;
    LocalDate endDate;
    SeriesOrganizer organizer;
    SeriesFormat format;
    int totalMatches;

}
