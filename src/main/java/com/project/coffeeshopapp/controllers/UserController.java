package com.project.coffeeshopapp.controllers;

import com.project.coffeeshopapp.dtos.request.user.UserCreateRequest;
import com.project.coffeeshopapp.dtos.request.user.UserLoginRequest;
import com.project.coffeeshopapp.dtos.request.user.UserSearchRequest;
import com.project.coffeeshopapp.dtos.request.user.UserUpdateRequest;
import com.project.coffeeshopapp.dtos.response.jwt.JwtResponse;
import com.project.coffeeshopapp.dtos.response.pagination.PaginationResponse;
import com.project.coffeeshopapp.dtos.response.role.RoleResponse;
import com.project.coffeeshopapp.dtos.response.user.AuthResponse;
import com.project.coffeeshopapp.dtos.response.user.UserResponse;
import com.project.coffeeshopapp.dtos.response.api.SuccessResponse;
import com.project.coffeeshopapp.dtos.response.user.UserSummaryResponse;
import com.project.coffeeshopapp.services.user.IUserService;
import com.project.coffeeshopapp.utils.PaginationUtil;
import com.project.coffeeshopapp.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final IUserService userService;
    private final ResponseUtil responseUtil;
    private final PaginationUtil paginationUtil;

    @PostMapping()
    public ResponseEntity<SuccessResponse<UserResponse>> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        UserResponse userResponse = userService.createUser(userCreateRequest);
        return responseUtil.createSuccessResponse(
                userResponse,
                "User was successfully created",
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<JwtResponse>> login(
            @Valid @RequestBody UserLoginRequest userLoginRequest,
            HttpServletResponse response) {
        JwtResponse jwtResponse = userService.login(userLoginRequest.getPhoneNumber(), userLoginRequest.getPassword());

        // Create Cookie from JWT
        ResponseCookie cookie = ResponseCookie.from("token", jwtResponse.getToken())
                .httpOnly(true)  // prevent XSS
                .secure(true)    // only HTTPS
                .path("/")
                .maxAge(24 * 60 * 60)  // expire after 1 day
                .sameSite("Strict")    // prevent CSRF
                .build();

        // Add Cookie to response
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return responseUtil.createSuccessResponse(
                jwtResponse,
                "Login successful",
                HttpStatus.OK
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<UserResponse>> updateUser(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        UserResponse userResponse = userService.updateUser(id, userUpdateRequest);
        return responseUtil.createSuccessResponse(
                userResponse,
                "User was successfully updated",
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<PaginationResponse<UserSummaryResponse>>> getAllUsers(
            @Valid @ModelAttribute UserSearchRequest request) {
        Page<UserSummaryResponse> userSummaryResponsePage = userService.getAllUsers(request);
        PaginationResponse<UserSummaryResponse> paginationResponse = paginationUtil.createPaginationResponse(
                userSummaryResponsePage
        );
        return responseUtil.createSuccessResponse(
                paginationResponse,
                "Retrieve all users successfully",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> deleteUser(@PathVariable(name = "id") Long id) {
        userService.softDeleteUser(id);
        return responseUtil.createSuccessResponseWithoutData(
                "User successfully deleted",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<UserResponse>> getUserById(
            @PathVariable(name = "id") Long id) {
        UserResponse userResponse = userService.getUserById(id);
        return responseUtil.createSuccessResponse(
                userResponse,
                "Get user successfully",
                HttpStatus.OK
        );
    }

    @GetMapping("/me")
    public ResponseEntity<SuccessResponse<AuthResponse>> getAuth() {
        AuthResponse authResponse = userService.getAuth();
        return responseUtil.createSuccessResponse(
                authResponse,
                "Get auth successfully",
                HttpStatus.OK
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<SuccessResponse<Void>> logout(HttpServletResponse response) {
        // create a cookie with the same name and set its maxAge to 0 to delete it
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true) // prevent XSS
                .secure(true) // only HTTPS
                .path("/")
                .maxAge(0) // instruct browser to delete the cookie
                .sameSite("Strict") // prevent CSRF
                .build();

        // add the cookie to the response, effectively deleting it on the client side
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return responseUtil.createSuccessResponse(
                null,
                "Logout successful",
                HttpStatus.OK
        );
    }
}