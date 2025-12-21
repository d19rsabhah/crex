package com.example.crex.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int tournamentId;

    @Column(nullable = false, unique = true)
    String title;

    LocalDate startDate;
    LocalDate endDate;
    String organizer;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    User createdBy;

    LocalDateTime createdAt;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "tournament",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    List<CricketMatch> matches;
}