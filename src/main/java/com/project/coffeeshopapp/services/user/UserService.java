package com.project.coffeeshopapp.services.user;

import com.project.coffeeshopapp.converters.UserConverter;
import com.project.coffeeshopapp.dtos.UserDTO;
import com.project.coffeeshopapp.models.User;
import com.project.coffeeshopapp.repositories.UserRepository;
import com.project.coffeeshopapp.validationservices.user.IUserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final IUserValidationService userValidationService;

    @Override
    public User createUser(UserDTO userDTO){
        // validate userDTO
        userValidationService.validateUserForCreation(userDTO);
        // convert from UserDTO to User
        User newUser = userConverter.convertToUser(userDTO);
        // save the new user
        return userRepository.save(newUser);
    }
}
