package com.testtaskalex.auth.validator;

import com.testtaskalex.auth.service.UserService;
import com.testtaskalex.dtos.resources.UserRegistrationResource;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(@NotNull Class<?> aClass) {
        return UserRegistrationResource.class.equals(aClass);
    }

    @Override
    public void validate(@NotNull Object o, @NotNull Errors errors) {
        UserRegistrationResource userRegistrationResource = (UserRegistrationResource) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (userRegistrationResource.getUsername().length() < 6 || userRegistrationResource.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(userRegistrationResource.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (userRegistrationResource.getPassword().length() < 8 || userRegistrationResource.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }


    }
}