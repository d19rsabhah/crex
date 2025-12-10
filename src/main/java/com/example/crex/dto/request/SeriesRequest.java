package com.example.crex.dto.request;

import java.time.LocalDate;

import com.example.crex.model.enums.SeriesFormat;
import com.example.crex.model.enums.SeriesOrganizer;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SeriesRequest {
    String title;          // e.g. "India vs Australia"
    String season;         // e.g. "2024-25"
    LocalDate startDate;
    LocalDate endDate;
    SeriesOrganizer organizer;     // e.g. BCCI, ICC, ECB
    SeriesFormat format;  // T20I / ODI / TEST
}
