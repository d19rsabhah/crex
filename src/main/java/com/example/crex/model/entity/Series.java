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
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int seriesId;

    @Column(nullable = false)
    String title;

    String season;
    LocalDate startDate;
    LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private SeriesFormat format;   // ✅ T20I / ODI / TEST

    @OneToMany(mappedBy = "series")
    List<CricketMatch> matches;
}