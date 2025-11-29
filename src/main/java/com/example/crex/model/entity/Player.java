package com.example.crex.model.entity;

import com.example.crex.model.enums.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Player {

    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    int playerId;

    @Column(nullable = false)
    String playerName;

    @Column(nullable = false)
    LocalDate dob;

    @Column(nullable = false, unique = true)
    String email;

    @Enumerated(EnumType.STRING)
    Gender gender;

    double height;

    double weight;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    Role role;

    int jerseyNumber;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    BattingStyle battingStyle;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    BowlingStyle bowlingStyle;

    LocalDate lastMatchDate;

    @Enumerated(EnumType.STRING)
    IsActive isActive;

    String nationality;

    String placeOfBirth;

    @ManyToOne
    @JoinColumn(name = "team_id")
    Team team;

    @ManyToOne
    @JoinColumn(name = "created_by")
    User createdBy;


}
