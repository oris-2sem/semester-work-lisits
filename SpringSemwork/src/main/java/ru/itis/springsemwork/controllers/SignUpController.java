package ru.itis.springsemwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.springsemwork.dto.SignUpForm;
import ru.itis.springsemwork.services.SignUpService;
import ru.itis.springsemwork.services.UsersService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class SignUpController {

    private final UsersService usersService;

    @GetMapping("/signUp")
    public String getSignUpPage(Authentication authentication, Model model) {
        if (authentication != null) {
            model.addAttribute("user", usersService.findByEmail(authentication.getName()));
            return "redirect:/items";
        }
        model.addAttribute("signUpForm", new SignUpForm());
        return "signUp";
    }

}
