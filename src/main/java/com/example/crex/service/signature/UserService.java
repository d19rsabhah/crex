package com.example.crex.service.signature;

import com.example.crex.dto.request.LogInRequest;
import com.example.crex.dto.request.UserRequest;
import com.example.crex.dto.response.LogInResponse;
import com.example.crex.dto.response.UserResponse;

public interface UserService {

    UserResponse addUser(UserRequest userRequest);
    LogInResponse logIn(LogInRequest logInRequest);

    LogInResponse logOut(String token);


}
