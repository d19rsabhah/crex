package com.example.crex.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int scoreId;

    @ManyToOne
    @JoinColumn(name = "match_id")
    CricketMatch match;

    @ManyToOne
    @JoinColumn(name = "team_id")
    Team team;

    int runs;
    int wickets;
    double overs;
    int extras;

    String currentStriker;
    String nonStriker;
    String currentBowler;
}
