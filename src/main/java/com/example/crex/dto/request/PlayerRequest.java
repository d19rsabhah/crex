package com.example.crex.dto.request;

import com.example.crex.model.enums.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
//public class PlayerRequest {
//    String playerName;
//    LocalDate dob;
//    String email;
//    Gender gender;
//    double height;
//    double weight;
//    Role role;
//    int jerseyNumber;
//    BattingStyle battingStyle;
//    BowlingStyle bowlingStyle;
//    String nationality;
//    String placeOfBirth;
//    int teamId;        // instead of Team entity
//
//}

public class PlayerRequest {
    private String playerName;
    private LocalDate dob;
    private String email;
    private Gender gender;
    private double height;
    private double weight;
    private Role role;
    private int jerseyNumber;
    private BattingStyle battingStyle;
    private BowlingStyle bowlingStyle;
    private LocalDate lastMatchDate;     // ADD
    private IsActive isActive;           // ADD
    private String nationality;
    private String placeOfBirth;
    private int teamId;
}

