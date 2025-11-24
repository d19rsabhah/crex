package com.example.crex.converter;


import com.example.crex.dto.request.TeamRequest;
import com.example.crex.dto.response.TeamResponse;
import com.example.crex.model.entity.Team;

public class TeamConverter {
    public static Team teamRequestToTeam(TeamRequest teamRequest){
        return Team.builder()
                .teamName(teamRequest.getTeamName())
                .logoUrl(teamRequest.getLogoUrl())
                .country(teamRequest.getCountry())
                .description(teamRequest.getDescription())
                .build();
    }

    public static TeamResponse teamToTeamResponse(Team team){

        int totalPlayers = (team.getPlayers() != null) ? team.getPlayers().size() : 0;

        String createdBy = (team.getCreatedBy() != null)
                ? team.getCreatedBy().getFullName()     // or `.getFullName()` depending on requirement
                : null;

        return TeamResponse.builder()
                .teamId(team.getTeamId())
                .teamName(team.getTeamName())
                .logoUrl(team.getLogoUrl())
                .country(team.getCountry())
                .description(team.getDescription())
                .createdBy(createdBy)
                .totalPlayers(totalPlayers)
                .build();
    }
}
