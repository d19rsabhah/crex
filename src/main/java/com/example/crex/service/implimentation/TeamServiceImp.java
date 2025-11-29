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
import org.springframework.dao.DataIntegrityViolationException;
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

//        // remove Bearer prefix
//        if (token.startsWith("Bearer "))
//            token = token.substring(7);
//
//        // extract email from JWT
//        String email = jwtService.extractUsername(token);
//
//        // find user
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//        // convert request to entity
//        Team team = TeamConverter.teamRequestToTeam(request);
//        team.setCreatedBy(user);
//
//        // save to DB
//        Team savedTeam = teamRepository.save(team);
//
//        // send mail to creator
//        try {
//            String subject = "Team Added Successfully ⚡";
//            String msg = "Hello " + user.getFullName() + ",\n\n" +
//                    "You have successfully added a team in CREX.\n" +
//                    "Team: " + savedTeam.getTeamName() + "\n" +
//                    "Country: " + savedTeam.getCountry() + "\n\n" +
//                    "Regards,\nTeam CREX";
//
//            emailService.sendEmail(user.getEmail(), subject, msg);
//        } catch (Exception e) {
//            System.out.println("Team email failed: " + e.getMessage());
//        }
//
//        // map to response DTO
//        return TeamConverter.teamToTeamResponse(savedTeam);


        try {
            // 1️⃣ Remove Bearer prefix
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // 2️⃣ Extract email from JWT
            String email = jwtService.extractUsername(token);
            if (email == null) {
                throw new RuntimeException("Invalid token. Please login again.");
            }

            // 3️⃣ Fetch User
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            // 4️⃣ Convert DTO → Entity
            Team team = TeamConverter.teamRequestToTeam(request);
            team.setCreatedBy(user);

            // 5️⃣ Save team safely with SQL exception handling
            Team savedTeam;
            try {
                savedTeam = teamRepository.save(team);
            } catch (DataIntegrityViolationException ex) {
                // This happens when duplicate team or unique key violation
                throw new RuntimeException("Team already exists. Please choose another name or country.");
            }

            // 6️⃣ Send email (email failure should NOT break API)
            try {
                String subject = "Team Added Successfully ⚡";
                String msg = "Hello " + user.getFullName() + ",\n\n" +
                        "A new team has been successfully created in CREX.\n\n" +
                        "Team: " + savedTeam.getTeamName() + "\n" +
                        "Country: " + savedTeam.getCountry() + "\n\n" +
                        "Regards,\nTeam CREX";

                emailService.sendEmail(user.getEmail(), subject, msg);
            } catch (Exception e) {
                System.out.println("⚠ Email sending failed: " + e.getMessage());
            }

            // 7️⃣ Convert to response DTO
            return TeamConverter.teamToTeamResponse(savedTeam);

        } catch (Exception e) {
            // ANY failure returns clean error instead of breaking app
            throw new RuntimeException("Failed to add team: " + e.getMessage());
        }

    }

    @Override
    public TeamResponse getTeamById(Integer teamId, String token) {

        if (token.startsWith("Bearer ")) token = token.substring(7);
        String email = jwtService.extractUsername(token);

        // verify requester is valid user
        userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        return TeamConverter.teamToTeamResponse(team);
    }

    // ------------------------------------------------------------------------------------
    // SEARCH BY TEAM NAME (PUBLIC)
    // ------------------------------------------------------------------------------------
    @Override
    public TeamResponse searchByName(String name) {

        Team team = teamRepository.findByTeamNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        return TeamConverter.teamToTeamResponse(team);
    }

    // ------------------------------------------------------------------------------------
    // SEARCH BY COUNTRY NAME (PUBLIC)
    // ------------------------------------------------------------------------------------
    @Override
    public TeamResponse searchByCountry(String country) {

        Team team = teamRepository.findByCountryIgnoreCase(country)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        return TeamConverter.teamToTeamResponse(team);
    }

    @Override
    public TeamResponse updateTeam(Integer teamId, TeamRequest request, String token) {

        // 1️⃣ Clean token
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        token = token.trim();

        // 2️⃣ Extract logged-in user email from JWT
        String email = jwtService.extractUsername(token);

        // 3️⃣ Verify user exists
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // 4️⃣ Fetch team
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        // 5️⃣ Apply partial updates
        if (request.getTeamName() != null)
            team.setTeamName(request.getTeamName());

        if (request.getCountry() != null)
            team.setCountry(request.getCountry());

        if (request.getDescription() != null)
            team.setDescription(request.getDescription());

        if (request.getLogoUrl() != null)
            team.setLogoUrl(request.getLogoUrl());

        // 6️⃣ Save updated record
        Team savedTeam = teamRepository.save(team);

        // 7️⃣ Send update email
        try {
            String subject = "Team Updated Successfully ✔";
            String msg = "Hello " + user.getFullName() + ",\n\n" +
                    "Team \"" + savedTeam.getTeamName() + "\" has been updated successfully.\n" +
                    "Updated by: " + user.getFullName() + "\n\n" +
                    "Regards,\nTeam CREX ⚡";

            emailService.sendEmail(user.getEmail(), subject, msg);

        } catch (Exception e) {
            System.out.println("⚠ Email sending failed: " + e.getMessage());
        }

        // 8️⃣ Convert to response DTO
        return TeamConverter.teamToTeamResponse(savedTeam);
    }


}
