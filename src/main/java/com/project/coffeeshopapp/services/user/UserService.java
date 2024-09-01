package com.project.coffeeshopapp.services.user;

import com.project.coffeeshopapp.dtos.request.user.UserCreateRequest;
import com.project.coffeeshopapp.dtos.response.jwt.JwtResponse;
import com.project.coffeeshopapp.dtos.response.user.UserResponse;
import com.project.coffeeshopapp.mappers.UserMapper;
import com.project.coffeeshopapp.models.CustomUserDetails;
import com.project.coffeeshopapp.models.User;
import com.project.coffeeshopapp.repositories.UserRepository;
import com.project.coffeeshopapp.utils.JwtUtil;
import com.project.coffeeshopapp.validationservices.user.IUserValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IUserValidationService userValidationService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    public UserResponse createUser(UserCreateRequest userCreateRequest){
        // validate userCreateRequest
        userValidationService.validateUserForCreation(userCreateRequest);
        // convert from userCreateRequest to User
        User newUser = userMapper.userCreateRequestToUser(userCreateRequest);
        // encode password
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        // save the new user
        User createdUser = userRepository.save(newUser);
        // convert from User to UserResponse
        return userMapper.userToUserResponse(createdUser);
    }

    @Override
    public JwtResponse login(String phoneNumber, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber, password
        );
        // authenticate with Java Spring Security
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(customUserDetails);
        return new JwtResponse(token);
    }
}
