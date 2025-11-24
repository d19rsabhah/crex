package com.example.crex.model.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int tournamentId;

    @Column(nullable = false, unique = true)
    String title;

    LocalDate startDate;
    LocalDate endDate;
    String organizer;

    @OneToMany(mappedBy = "tournament")
    List<CricketMatch> matches;
}