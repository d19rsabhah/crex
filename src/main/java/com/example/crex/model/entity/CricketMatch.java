package com.example.crex.model.entity;

import com.example.crex.model.enums.MatchStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CricketMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int matchId;

    String venue;

    LocalDateTime matchDate;

    String tossWinner;
    String tossDecision;

    @Enumerated(EnumType.STRING)
    MatchStatus status;  // SCHEDULED / LIVE / COMPLETED

    String result;

    @ManyToMany
    @JoinTable(
            name = "match_team",
            joinColumns = @JoinColumn(name = "match_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    List<Team> teams;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    Tournament tournament; // nullable

    @ManyToOne
    @JoinColumn(name = "series_id")
    Series series; // nullable

}
