package com.testtaskalex.controllers;

import com.testtaskalex.auth.service.SecurityService;
import com.testtaskalex.auth.service.UserService;
import com.testtaskalex.auth.validator.UserValidator;
import com.testtaskalex.dtos.UserDto;
import com.testtaskalex.dtos.resources.UserRegistrationResource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @PostMapping("api/v1/registration")
    public UserDto registration(@Valid @RequestBody UserRegistrationResource userRegistrationResourceResource,
                                BindingResult bindingResult) {
        userValidator.validate(userRegistrationResourceResource, bindingResult);

        UserDto userDto = userService.createUser(userRegistrationResourceResource);

        securityService.autoLogin(userRegistrationResourceResource.getUsername(),
                userRegistrationResourceResource.getPassword());

        return userDto;
    }

    @PostMapping("api/v1/changerole")
    public UserDto changeRole(@Valid @RequestBody UserRegistrationResource userRegistrationResourceResource,
                              BindingResult bindingResult) {
        userValidator.validate(userRegistrationResourceResource, bindingResult);

        UserDto userDto = userService.changeUserRole(userRegistrationResourceResource);

        securityService.autoLogin(userRegistrationResourceResource.getUsername(),
                userRegistrationResourceResource.getPassword());

        return userDto;
    }

    @GetMapping("api/v1/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

}