package com.example.crex.service.implimentation;

import com.example.crex.config.JwtService;
import com.example.crex.converter.PlayerConverter;
import com.example.crex.dto.request.PlayerRequest;
import com.example.crex.dto.response.PlayerResponse;
import com.example.crex.exception.DataIntegrityViolationException;
import com.example.crex.exception.ResourceNotFoundException;
import com.example.crex.model.entity.Player;
import com.example.crex.model.entity.Team;
import com.example.crex.model.entity.User;
import com.example.crex.repository.PlayerRepository;
import com.example.crex.repository.TeamRepository;
import com.example.crex.repository.UserRepository;
import com.example.crex.service.signature.EmailService;
import com.example.crex.service.signature.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
//public class PlayerServiceImp implements PlayerService {
//
//    @Autowired
//    JwtService jwtService;
//
//    @Autowired
//    PlayerRepository playerRepository;
//
//    @Autowired
//    TeamRepository teamRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    EmailServiceImp emailServiceImp;
//
//
//    @Override
//    public PlayerResponse addPlayer(PlayerRequest request, Integer teamId, String token) {
//
//        // Remove Bearer prefix if exists
//        if (token.startsWith("Bearer ")) {
//            token = token.substring(7);
//        }
//
//        // Extract user email from token
//        String email = jwtService.extractUsername(token);
//
//        // Fetch the user who is adding player
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//        // Check if team exists
//        Team team = teamRepository.findById(teamId)
//                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
//
//        // Convert request DTO → Entity
//        Player player = PlayerConverter.playerRequestToPlayer(request);
//        player.setTeam(team);
//        player.setCreatedBy(user);
//
//        // Save player
//        Player savedPlayer = playerRepository.save(player);
//
//        // Convert to response DTO
//        PlayerResponse response = PlayerConverter.playerToPlayerResponse(savedPlayer);
//// send mail to PLAYER
//        try {
//            String subject1 = "Welcome to CREX! 🎉";
//            String msg1 = "Hello " + savedPlayer.getPlayerName() + ",\n\n" +
//                    "You have been successfully added as a player in CREX.\n" +
//                    "Team: " + team.getTeamName() + "\n" +
//                    "Role: " + savedPlayer.getRole() + "\n\n" +
//                    "We are excited to see your performance!\n\n" +
//                    "Regards,\nTeam CREX ⚡";
//
//            emailServiceImp.sendEmail(savedPlayer.getEmail(), subject1, msg1);
//        } catch (Exception e) {
//            System.out.println("Email sending to PLAYER failed: " + e.getMessage());
//        }
//
//        // send mail to USER (who added the player)
//        try {
//            String subject2 = "Player Added Successfully ✔";
//            String msg2 = "Hello " + user.getFullName() + ",\n\n" +
//                    "You have successfully added player \"" + savedPlayer.getPlayerName() + "\" to CREX.\n" +
//                    "Team: " + team.getTeamName() + "\n\n" +
//                    "Thank you for your contribution!\n\n" +
//                    "Regards,\nTeam CREX ⚡";
//
//            emailServiceImp.sendEmail(user.getEmail(), subject2, msg2);
//        } catch (Exception e) {
//            System.out.println("Email sending to USER failed: " + e.getMessage());
//        }
//
//        return response;
//    }
//}

@Service
@RequiredArgsConstructor
public class PlayerServiceImp implements PlayerService {

    private final JwtService jwtService;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public PlayerResponse addPlayer(PlayerRequest request, Integer teamId, String token) {

        try {
            // 1️⃣ Clean and validate token
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            token = token.trim();

            String email;
            try {
                email = jwtService.extractUsername(token);
            } catch (Exception ex) {
                throw new RuntimeException("Invalid or expired token. Please login again.");
            }

            // 2️⃣ Fetch the logged-in user
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            // 3️⃣ Fetch team
            Team team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

            // 4️⃣ Convert DTO → Entity
            Player player = PlayerConverter.playerRequestToPlayer(request);
            player.setTeam(team);
            player.setCreatedBy(user);

            // 5️⃣ Save player safely
            Player savedPlayer;
            try {
                savedPlayer = playerRepository.save(player);
            } catch (DataIntegrityViolationException ex) {
                throw new RuntimeException("Duplicate player or invalid data.");
            }

            // 6️⃣ Email → PLAYER
            try {
                String subject1 = "Welcome to CREX! 🎉";
                String msg1 = "Hello " + savedPlayer.getPlayerName() + ",\n\n" +
                        "You have been successfully added to team: " + team.getTeamName() + "\n" +
                        "Role: " + savedPlayer.getRole() + "\n\n" +
                        "Regards,\nTeam CREX ⚡";

                emailService.sendEmail(savedPlayer.getEmail(), subject1, msg1);
            } catch (Exception e) {
                System.out.println("⚠ Email to PLAYER failed: " + e.getMessage());
            }

            // 7️⃣ Email → USER (who added the player)
            try {
                String subject2 = "Player Added Successfully ✔";
                String msg2 = "Hello " + user.getFullName() + ",\n\n" +
                        "You successfully added player \"" + savedPlayer.getPlayerName() + "\".\n" +
                        "Team: " + team.getTeamName() + "\n\n" +
                        "Regards,\nTeam CREX ⚡";

                emailService.sendEmail(user.getEmail(), subject2, msg2);
            } catch (Exception e) {
                System.out.println("⚠ Email to USER failed: " + e.getMessage());
            }

            // 8️⃣ Convert & return DTO
            return PlayerConverter.playerToPlayerResponse(savedPlayer);

        } catch (Exception e) {
            throw new RuntimeException("Failed to add player: " + e.getMessage());
        }
    }
}
