package com.example.crex.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScoreRequest {
    int teamId;
    int runs;
    int wickets;
    double overs;
    int extras;
    String currentStriker;
    String nonStriker;
    String currentBowler;
}
