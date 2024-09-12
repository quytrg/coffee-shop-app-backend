package com.project.coffeeshopapp.validationservices.user;

import com.project.coffeeshopapp.dtos.request.user.UserCreateRequest;
import com.project.coffeeshopapp.dtos.request.user.UserUpdateRequest;
import com.project.coffeeshopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidationService implements IUserValidationService {
    private final UserRepository userRepository;

    @Override
    public void validateUserForCreation(UserCreateRequest userCreateRequest) {
//        // check if password matches retype-password
//        if (!userCreateRequest.getPassword().equals(userCreateRequest.getRetypePassword())) {
//            throw new PasswordMismatchException();
//        }
//
//        // check if phoneNumber already exists
//        if(userRepository.existsByPhoneNumber(userCreateRequest.getPhoneNumber())) {
//            throw new DataIntegrityViolationException("Phone number already exists");
//        }
    }

    @Override
    public void validateUserForUpdate(UserUpdateRequest userUpdateRequest) {
//        // check if password matches retype-password
//        if (!StringUtil.isEmpty(userUpdateRequest.getPassword()) &&
//                !StringUtil.isEmpty(userUpdateRequest.getRetypePassword()) &&
//                !userUpdateRequest.getPassword().equals(userUpdateRequest.getRetypePassword())) {
//            throw new PasswordMismatchException();
//        }
//
//        // check if phoneNumber already exists
//        if(!StringUtil.isEmpty(userUpdateRequest.getPhoneNumber()) &&
//                userRepository.existsByPhoneNumber(userUpdateRequest.getPhoneNumber())) {
//            throw new DataIntegrityViolationException("Phone number already exists");
//        }
    }
}
