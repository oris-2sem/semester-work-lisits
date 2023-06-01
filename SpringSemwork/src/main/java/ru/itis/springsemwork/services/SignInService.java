package ru.itis.springsemwork.services;

import ru.itis.springsemwork.dto.SignInForm;
import ru.itis.springsemwork.models.User;

public interface SignInService {
    User signIn(SignInForm signInForm);
}
