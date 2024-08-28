package com.project.coffeeshopapp.services.user;

import com.project.coffeeshopapp.dtos.UserDTO;
import com.project.coffeeshopapp.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO);
}
