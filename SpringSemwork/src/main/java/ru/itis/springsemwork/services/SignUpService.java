package ru.itis.springsemwork.services;

import ru.itis.springsemwork.dto.SignUpForm;
import ru.itis.springsemwork.dto.UserDto;

public interface SignUpService {
    UserDto signUp(SignUpForm form);
}
