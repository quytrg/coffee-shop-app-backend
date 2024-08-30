package com.project.coffeeshopapp.services.user;

import com.project.coffeeshopapp.dtos.request.user.UserCreateRequest;
import com.project.coffeeshopapp.dtos.response.user.UserResponse;
import com.project.coffeeshopapp.mappers.UserMapper;
import com.project.coffeeshopapp.models.User;
import com.project.coffeeshopapp.repositories.UserRepository;
import com.project.coffeeshopapp.validationservices.user.IUserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IUserValidationService userValidationService;

    @Override
    public UserResponse createUser(UserCreateRequest userCreateRequest){
        // validate userCreateRequest
        userValidationService.validateUserForCreation(userCreateRequest);
        // convert from userCreateRequest to User
        User newUser = userMapper.userCreateRequestToUser(userCreateRequest);
        // save the new user
        User createdUser = userRepository.save(newUser);
        // convert from User to UserResponse
        return userMapper.userToUserResponse(createdUser);
    }
}
