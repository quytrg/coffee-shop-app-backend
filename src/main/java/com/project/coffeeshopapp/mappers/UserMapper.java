package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.constants.AppConstants;
import com.project.coffeeshopapp.dtos.request.user.UserCreateRequest;
import com.project.coffeeshopapp.dtos.response.user.UserResponse;
import com.project.coffeeshopapp.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "dateOfBirth", source = "dateOfBirth", dateFormat = AppConstants.DATE_FORMAT)
    @Mapping(target = "isActive", source = "isActive", defaultExpression = "java(true)")
    User userCreateRequestToUser(UserCreateRequest userCreateRequest);

    @Mapping(target = "dateOfBirth", source = "dateOfBirth", dateFormat = AppConstants.DATE_FORMAT)
    UserResponse userToUserResponse(User user);
}
