package com.example.crex.service.implimentation;

import com.example.crex.config.JwtService;
import com.example.crex.converter.PlayerConverter;
import com.example.crex.dto.request.PlayerRequest;
import com.example.crex.dto.response.PlayerResponse;
import com.example.crex.exception.ResourceNotFoundException;
import com.example.crex.model.entity.Player;
import com.example.crex.model.entity.Team;
import com.example.crex.model.entity.User;
import com.example.crex.repository.PlayerRepository;
import com.example.crex.repository.TeamRepository;
import com.example.crex.repository.UserRepository;
import com.example.crex.service.signature.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImp implements PlayerService {

    @Autowired
    JwtService jwtService;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailServiceImp emailServiceImp;


    @Override
    public PlayerResponse addPlayer(PlayerRequest request, Integer teamId, String token) {

        // Remove Bearer prefix if exists
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Extract user email from token
        String email = jwtService.extractUsername(token);

        // Fetch the user who is adding player
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Check if team exists
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        // Convert request DTO → Entity
        Player player = PlayerConverter.playerRequestToPlayer(request);
        player.setTeam(team);
        player.setCreatedBy(user);

        // Save player
        Player savedPlayer = playerRepository.save(player);

        // Convert to response DTO
        PlayerResponse response = PlayerConverter.playerToPlayerResponse(savedPlayer);
// send mail to PLAYER
        try {
            String subject1 = "Welcome to CREX! 🎉";
            String msg1 = "Hello " + savedPlayer.getPlayerName() + ",\n\n" +
                    "You have been successfully added as a player in CREX.\n" +
                    "Team: " + team.getTeamName() + "\n" +
                    "Role: " + savedPlayer.getRole() + "\n\n" +
                    "We are excited to see your performance!\n\n" +
                    "Regards,\nTeam CREX ⚡";

            emailServiceImp.sendEmail(savedPlayer.getEmail(), subject1, msg1);
        } catch (Exception e) {
            System.out.println("Email sending to PLAYER failed: " + e.getMessage());
        }

        // send mail to USER (who added the player)
        try {
            String subject2 = "Player Added Successfully ✔";
            String msg2 = "Hello " + user.getFullName() + ",\n\n" +
                    "You have successfully added player \"" + savedPlayer.getPlayerName() + "\" to CREX.\n" +
                    "Team: " + team.getTeamName() + "\n\n" +
                    "Thank you for your contribution!\n\n" +
                    "Regards,\nTeam CREX ⚡";

            emailServiceImp.sendEmail(user.getEmail(), subject2, msg2);
        } catch (Exception e) {
            System.out.println("Email sending to USER failed: " + e.getMessage());
        }

        return response;
    }
}