package com.example.crex.service.signature;

import com.example.crex.dto.request.TeamRequest;
import com.example.crex.dto.response.TeamResponse;

public interface TeamService {
    TeamResponse addTeam(TeamRequest request, String token);

}
