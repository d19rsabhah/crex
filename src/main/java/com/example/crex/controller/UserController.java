package com.example.crex.controller;


import com.example.crex.config.JwtService;
import com.example.crex.dto.request.LogInRequest;
import com.example.crex.dto.request.UserRequest;
import com.example.crex.dto.response.LogInResponse;
import com.example.crex.dto.response.UserResponse;
import com.example.crex.service.signature.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/user/registration")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.addUser(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/user/logIn")
    public ResponseEntity<?> logIn(@RequestBody LogInRequest loginRequest) {
        LogInResponse response = userService.logIn(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/user/logOut")
    public ResponseEntity<?> logout(HttpServletRequest request) {

        String token = jwtService.extractToken(request);

        if (token == null) {
            return ResponseEntity.badRequest().body("Token missing");
        }

        LogInResponse response = userService.logOut(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
