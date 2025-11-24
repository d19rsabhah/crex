package com.example.crex.dto.request;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeriesRequest {
    String title;
    String season;
    LocalDate startDate;
    LocalDate endDate;
    String organizer;

}
