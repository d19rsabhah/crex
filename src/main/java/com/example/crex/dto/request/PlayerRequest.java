package com.example.crex.dto.request;

import com.example.crex.model.enums.BattingStyle;
import com.example.crex.model.enums.BowlingStyle;
import com.example.crex.model.enums.Gender;
import com.example.crex.model.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PlayerRequest {
    String playerName;
    LocalDate dob;
    String email;
    Gender gender;
    double height;
    double weight;
    Role role;
    int jerseyNumber;
    BattingStyle battingStyle;
    BowlingStyle bowlingStyle;
    String nationality;
    String placeOfBirth;
    int teamId;        // instead of Team entity

}
