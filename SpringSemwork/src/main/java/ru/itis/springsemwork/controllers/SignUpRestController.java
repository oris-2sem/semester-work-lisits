package ru.itis.springsemwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.springsemwork.controllers.api.SignUpApi;
import ru.itis.springsemwork.dto.SignUpForm;
import ru.itis.springsemwork.dto.UserDto;
import ru.itis.springsemwork.services.SignUpService;

@RestController
@RequiredArgsConstructor
public class SignUpRestController implements SignUpApi {

    private final SignUpService signUpService;

    @Override
    public ResponseEntity<UserDto> signUpUser(@RequestHeader("X-CSRF-TOKEN") String token, @RequestBody SignUpForm user) {
        return ResponseEntity.ok().body(signUpService.signUp(user));
    }
}
