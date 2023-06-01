package ru.itis.springsemwork.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.springsemwork.dto.SignUpForm;
import ru.itis.springsemwork.dto.UserDto;
import ru.itis.springsemwork.dto.converters.UserConverter;
import ru.itis.springsemwork.models.Role;
import ru.itis.springsemwork.models.User;
import ru.itis.springsemwork.repositories.UsersRepository;
import ru.itis.springsemwork.validation.EmailValidator;

import java.io.IOException;
import java.util.Collections;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService{

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;
    private final EmailValidator emailValidator;

    @Override
    public UserDto signUp(SignUpForm form) {

//        String token = JWT.create()
//                .withClaim("email", form.getEmail())
//                .withClaim("password", form.getPassword())
//                .sign(Algorithm.HMAC256("secret_key_tea"));
//
        try {
            boolean isEmailValid = emailValidator.validateEmail(form.getEmail());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        User user = User.builder()
                .name(form.getName())
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .password(passwordEncoder.encode(form.getPassword()))
                .roles(Collections.singleton(Role.USER))
                .state(User.State.CONFIRMED)
                .personalDiscount(0)
                .build();

        usersRepository.save(user);
        return userConverter.from(user);
    }

}
