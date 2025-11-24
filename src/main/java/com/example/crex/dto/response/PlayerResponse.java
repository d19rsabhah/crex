package com.example.crex.dto.response;

import com.example.crex.model.enums.Gender;
import com.example.crex.model.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerResponse {

    int playerId;
    String playerName;
    String email;
    Gender gender;
    Role role;
    int jerseyNumber;
    String nationality;
    String teamName;
}
