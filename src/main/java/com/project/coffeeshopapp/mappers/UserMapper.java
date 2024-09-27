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
    @Mapping(target = "role", ignore = true)
    User userCreateRequestToUser(UserCreateRequest userCreateRequest);

    @Mapping(target = "dateOfBirth", source = "dateOfBirth", dateFormat = AppConstants.DATE_FORMAT)
    UserResponse userToUserResponse(User user);
    //  mapping beans as child beans Role -> RoleSummaryResponse
    RoleSummaryResponse roleToRoleSummaryResponse(Role role);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    void userUpdateRequestToUser(UserUpdateRequest userUpdateRequest, @MappingTarget User user);

    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.name", target = "roleName")
    UserSummaryResponse userToUserSummaryResponse(User user);

}
