package com.example.crex.service.implimentation;

import com.example.crex.config.JwtService;
import com.example.crex.converter.UserConverter;
import com.example.crex.dto.request.LogInRequest;
import com.example.crex.dto.request.UserRequest;
import com.example.crex.dto.response.LogInResponse;
import com.example.crex.dto.response.UserResponse;
import com.example.crex.exception.ResourceNotFoundException;
import com.example.crex.model.entity.User;
import com.example.crex.model.enums.User_Role;
import com.example.crex.repository.UserRepository;
import com.example.crex.service.signature.EmailService;
import com.example.crex.service.signature.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public UserResponse addUser(UserRequest userRequest) {

        // Convert request DTO → entity
        User user = UserConverter.userRequestToUser(userRequest);

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Default role for registration (you can change later)
        if (user.getUserRole() == null) {
            user.setUserRole(User_Role.USER);
        }
        user.setCreatedDate(LocalDateTime.now());
        user.setLastLogin(null);

        // Save user
        User savedUser = userRepository.save(user);

        String subject = "Welcome to CREX 🎉";
        String message = "Hello " + savedUser.getFullName() + ",\n\n"
                + "Your registration to Cricket Buzz was successful.\n"
                + "You can now log in using your registered email.\n\n"
                + "Thank you for joining! 🙌";
        try {
            emailService.sendEmail(savedUser.getEmail(), subject, message);
        } catch (Exception e) {
            System.out.println("Email sending failed: " + e.getMessage());
        }

        // Convert back to response DTO → return
        return UserConverter.userToUserResponse(savedUser);
    }

    @Override
    public LogInResponse logIn(LogInRequest loginRequest) {

        // authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // fetch user from DB
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // update lastLogin time
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // generate JWT token
        String token = jwtService.generateToken(user);

        System.out.println("Login email sending to: " + user.getEmail());

        String subject = "Login Activity on CREX ✔";
        String message =
                "Hello " + user.getFullName() + ",\n\n" +
                        "You have successfully logged into your CREX account just now.\n" +
                        "If this was not you, please reset your password immediately.\n\n" +
                        "Login Time: " + LocalDateTime.now() + "\n\n" +
                        "Regards,\nTeam CREX ⚡";

        try {
            emailService.sendEmail(user.getEmail(), subject, message);
        } catch (Exception e) {
            System.out.println("Login email failed: " + e.getMessage());
        }

        return new LogInResponse(token, "Login successful");
    }

    @Override
    public LogInResponse logOut(String token) {

        String email = jwtService.extractUsername(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        System.out.println("Logout email sending to: " + user.getEmail());

        // send logout email
        String subject = "Logout Activity on CREX ✔";
        String message =
                "Hello " + user.getFullName() + ",\n\n" +
                        "You have successfully logged out from your CREX account.\n" +
                        "If this was not you, please change your password immediately.\n\n" +
                        "Logout Time: " + LocalDateTime.now() + "\n\n" +
                        "Regards,\nTeam CREX ⚡";

        try {
            emailService.sendEmail(user.getEmail(), subject, message);
        } catch (Exception e) {
            System.out.println("Logout email failed: " + e.getMessage());
        }

        return new LogInResponse(null, "Logout successful");
    }


}
