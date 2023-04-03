package com.testtaskalex.auth.service;

import com.testtaskalex.dtos.UserDto;
import com.testtaskalex.dtos.resources.UserRegistrationResource;
import com.testtaskalex.persistance.entities.User;

public interface UserService {
    UserDto createUser(UserRegistrationResource userRegistrationResource);

    User findByUsername(String username);

    UserDto changeUserRole(UserRegistrationResource userRegistrationResourceResource);
}