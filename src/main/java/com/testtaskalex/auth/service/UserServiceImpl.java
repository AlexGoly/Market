package com.testtaskalex.auth.service;

import com.testtaskalex.auth.repository.RoleRepository;
import com.testtaskalex.auth.repository.UserRepository;
import com.testtaskalex.constants.Constants;
import com.testtaskalex.dtos.UserDto;
import com.testtaskalex.dtos.resources.UserRegistrationResource;
import com.testtaskalex.enums.UserRole;
import com.testtaskalex.exceptions.ResourceNotFoundException;
import com.testtaskalex.mappers.UserMapper;
import com.testtaskalex.persistance.entities.Role;
import com.testtaskalex.persistance.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.testtaskalex.constants.Constants.NOT_FOUND_ROLE_MANAGER;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto createUser(UserRegistrationResource userRegistrationResourceResource) {
        Set<Role> roles = new HashSet<>();
        User user = new User();
        user.setUsername(userRegistrationResourceResource.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userRegistrationResourceResource.getPassword()));

        Role role = roleRepository.findByName(UserRole.USER.name()).orElseThrow(() ->
                new ResourceNotFoundException(
                        String.format(Constants.NOT_FOUND_ROLE_USER)));
        roles.add(role);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDto changeUserRole(UserRegistrationResource userRegistrationResourceResource) {
        Set<Role> roles = new HashSet<>();
        User user = findByUsername(userRegistrationResourceResource.getUsername());
        Role role = roleRepository.findByName(UserRole.MANAGER.name())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(NOT_FOUND_ROLE_MANAGER)));

        roles.add(role);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }
}