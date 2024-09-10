package com.project.coffeeshopapp.mappers;

import com.project.coffeeshopapp.constants.AppConstants;
import com.project.coffeeshopapp.dtos.request.user.UserCreateRequest;
import com.project.coffeeshopapp.dtos.request.user.UserUpdateRequest;
import com.project.coffeeshopapp.dtos.response.role.RoleSummaryResponse;
import com.project.coffeeshopapp.dtos.response.user.UserResponse;
import com.project.coffeeshopapp.dtos.response.user.UserSummaryResponse;
import com.project.coffeeshopapp.models.Role;
import com.project.coffeeshopapp.models.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "dateOfBirth", source = "dateOfBirth", dateFormat = AppConstants.DATE_FORMAT)
    @Mapping(target = "isActive", source = "isActive", defaultExpression = "java(true)")
    @Mapping(target = "role", ignore = true)
    User userCreateRequestToUser(UserCreateRequest userCreateRequest);

    @Mapping(target = "dateOfBirth", source = "dateOfBirth", dateFormat = AppConstants.DATE_FORMAT)
    UserResponse userToUserResponse(User user);
    //  mapping beans as child beans of User and UserResponse (Role -> RoleSummaryResponse)
    RoleSummaryResponse roleToRoleSummaryResponse(Role role);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", ignore = true)
    void userUpdateRequestToUser(UserUpdateRequest userUpdateRequest, @MappingTarget User user);

    UserSummaryResponse userToUserSummaryResponse(User user);

}
