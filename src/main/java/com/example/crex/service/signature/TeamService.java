package com.example.crex.service.signature;

import com.example.crex.dto.request.TeamRequest;
import com.example.crex.dto.response.TeamResponse;

import java.util.List;

public interface TeamService {
    TeamResponse addTeam(TeamRequest request, String token);
    TeamResponse getTeamById(Integer id, String token);     // USER + ADMIN
    TeamResponse searchByName(String name);// PUBLIC
    TeamResponse searchByCountry(String country);
    TeamResponse updateTeam(Integer teamId, TeamRequest request, String token);


}
