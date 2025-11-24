package com.example.crex.dto.response;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScoreResponse {
    int teamId;
    String teamName;
    int runs;
    int wickets;
    double overs;
    int extras;
    String currentStriker;
    String nonStriker;
    String currentBowler;
}
