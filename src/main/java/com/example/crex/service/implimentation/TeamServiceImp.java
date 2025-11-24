package com.example.crex.service.implimentation;
import com.example.crex.config.JwtService;
import com.example.crex.converter.TeamConverter;
import com.example.crex.dto.request.TeamRequest;
import com.example.crex.dto.response.TeamResponse;
import com.example.crex.exception.ResourceNotFoundException;
import com.example.crex.model.entity.Team;
import com.example.crex.model.entity.User;
import com.example.crex.repository.TeamRepository;
import com.example.crex.repository.UserRepository;
import com.example.crex.service.signature.EmailService;
import com.example.crex.service.signature.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamServiceImp implements TeamService{

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Override
    public TeamResponse addTeam(TeamRequest request, String token) {

        // remove Bearer prefix
        if (token.startsWith("Bearer "))
            token = token.substring(7);

        // extract email from JWT
        String email = jwtService.extractUsername(token);

        // find user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // convert request to entity
        Team team = TeamConverter.teamRequestToTeam(request);
        team.setCreatedBy(user);

        // save to DB
        Team savedTeam = teamRepository.save(team);

        // send mail to creator
        try {
            String subject = "Team Added Successfully ⚡";
            String msg = "Hello " + user.getFullName() + ",\n\n" +
                    "You have successfully added a team in CREX.\n" +
                    "Team: " + savedTeam.getTeamName() + "\n" +
                    "Country: " + savedTeam.getCountry() + "\n\n" +
                    "Regards,\nTeam CREX";

            emailService.sendEmail(user.getEmail(), subject, msg);
        } catch (Exception e) {
            System.out.println("Team email failed: " + e.getMessage());
        }

        // map to response DTO
        return TeamConverter.teamToTeamResponse(savedTeam);

    }
}
