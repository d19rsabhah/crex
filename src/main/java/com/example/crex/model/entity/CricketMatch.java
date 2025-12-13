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
/*public class CricketMatch {

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

 */
public class CricketMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer matchId;

    @Column(nullable = false)
    String matchTitle;   // e.g. "IND vs AUS - 1st ODI"

    @Column(nullable = false)
    String venue;

    @Column(nullable = false)
    LocalDateTime matchDate;

    // =========================
    // TEAMS (EXACTLY TWO)
    // =========================
    @ManyToOne(optional = false)
    @JoinColumn(name = "team1_id")
    Team team1;

    @ManyToOne(optional = false)
    @JoinColumn(name = "team2_id")
    Team team2;

    // =========================
    // MATCH INFO
    // =========================
    String tossWinner;    // team name
    String tossDecision;  // BAT / BOWL

    @Enumerated(EnumType.STRING)
    MatchStatus status;   // SCHEDULED / LIVE / COMPLETED

    String result;        // "India won by 6 wickets"

    // =========================
    // RELATION (EITHER ONE)
    // =========================
    @ManyToOne
    @JoinColumn(name = "series_id")
    Series series;        // nullable

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    Tournament tournament; // nullable
}