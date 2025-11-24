package com.example.crex.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TeamRequest {
    String teamName;
    String logoUrl;
    String country;
    String description;

}
