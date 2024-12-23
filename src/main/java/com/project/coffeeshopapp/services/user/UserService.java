package com.project.coffeeshopapp.services.user;

import com.project.coffeeshopapp.customexceptions.DataNotFoundException;
import com.project.coffeeshopapp.customexceptions.UnauthorizedException;
import com.project.coffeeshopapp.dtos.request.user.UserCreateRequest;
import com.project.coffeeshopapp.dtos.request.user.UserSearchRequest;
import com.project.coffeeshopapp.dtos.request.user.UserUpdateRequest;
import com.project.coffeeshopapp.dtos.response.jwt.JwtResponse;
import com.project.coffeeshopapp.dtos.response.user.AuthResponse;
import com.project.coffeeshopapp.dtos.response.user.UserResponse;
import com.project.coffeeshopapp.dtos.response.user.UserSummaryResponse;
import com.project.coffeeshopapp.mappers.UserMapper;
import com.project.coffeeshopapp.models.CustomUserDetails;
import com.project.coffeeshopapp.models.Role;
import com.project.coffeeshopapp.models.User;
import com.project.coffeeshopapp.repositories.RoleRepository;
import com.project.coffeeshopapp.repositories.UserRepository;
import com.project.coffeeshopapp.services.imageassociation.IImageAssociationService;
import com.project.coffeeshopapp.specifications.UserSpecification;
import com.project.coffeeshopapp.utils.JwtUtil;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RoleRepository roleRepository;
    private final PaginationUtil paginationUtil;
    private final SortUtil sortUtil;
    private final IImageAssociationService imageAssociationService;

    @Override
    @Transactional
    public UserResponse createUser(UserCreateRequest userCreateRequest){
        // convert from userCreateRequest to User
        User newUser = userMapper.userCreateRequestToUser(userCreateRequest);

        // encode password
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        // check if roleId exists
        Role role = roleRepository.findById(userCreateRequest.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("role", "Role not found with id: " + userCreateRequest.getRoleId()));
        // set role for user
        newUser.setRole(role);

        // associate images with user
        imageAssociationService.createImageAssociations(newUser, userCreateRequest.getImageIds());

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

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findByIdWithRole(id)
                .orElseThrow(() -> new DataNotFoundException("user", "Cannot find user with id " + id));
        // convert userUpdateRequest to user
        userMapper.userUpdateRequestToUser(userUpdateRequest, user);
        // update the role if roleId presents and exists
        Optional.ofNullable(userUpdateRequest.getRoleId())
                .ifPresent(roleId -> {
                    Role role = roleRepository.findById(roleId)
                            .orElseThrow(() -> new DataNotFoundException("role", "Role not found with id: " + roleId));
                    user.setRole(role);
                });
        // hash password if it presents
        Optional.ofNullable(userUpdateRequest.getPassword())
                .ifPresent(password -> user.setPassword(passwordEncoder.encode(password)));
        // Handle image associations
        Optional.ofNullable(userUpdateRequest.getImageIds())
                .ifPresent(imageIds -> imageAssociationService.updateImageAssociations(user, imageIds));
        // save update
        User updatedUser = userRepository.save(user);
        return userMapper.userToUserResponse(updatedUser);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserSummaryResponse> getAllUsers(UserSearchRequest request) {
        Sort sort = sortUtil.createSort(
                request.getSortBy(),
                request.getSortDir()
        );
        Pageable pageable = paginationUtil.createPageable(
                request.getPage(),
                request.getSize(),
                sort
        );
        Specification<User> spec = UserSpecification.builder()
                .roleId(request.getRoleId())
                .keyword(request.getKeyword())
                .status(request.getStatus())
                .sex(request.getSex())
                .roleIds(request.getRoleIds())
                .build();
        Page<User> userPage = userRepository.findAll(spec, pageable);
        return userPage.map(userMapper::userToUserSummaryResponse);
    }

    @Transactional
    @Override
    public void softDeleteUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("user" ,"User not found with id: " + id));
        userRepository.softDelete(id);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findByIdWithRole(id)
                .orElseThrow(() -> new DataNotFoundException("user", "User not found with id: " + id));
        return userMapper.userToUserResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse getAuth() {
        // retrieves auth
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // check if the user is authenticated
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails userDetails) {
            User user = userDetails.getUser();
            return userMapper.userToAuthResponse(user);
        }
        else {
            throw new UnauthorizedException("Invalid user details");
        }
    }

}
