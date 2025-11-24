package com.example.crex.converter;

import com.example.crex.dto.request.UserRequest;
import com.example.crex.dto.response.UserResponse;
import com.example.crex.model.entity.User;

public class UserConverter {

    public static User userRequestToUser(UserRequest userRequest){
        return User.builder()
                .fullName(userRequest.getFullName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();
    }

    public static UserResponse userToUserResponse(User user){
        return UserResponse.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .build();
    }

}
