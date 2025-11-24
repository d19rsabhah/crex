package com.example.crex.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TeamResponse {
    int teamId;
    String teamName;
    String logoUrl;
    String country;
    String description;
    String createdBy;   // creator's name or email
    int totalPlayers;

}
