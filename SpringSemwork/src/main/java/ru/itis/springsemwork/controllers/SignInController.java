package ru.itis.springsemwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.springsemwork.dto.SignInForm;
import ru.itis.springsemwork.models.User;
import ru.itis.springsemwork.security.mvc.MvcSecurityConfiguration;
import ru.itis.springsemwork.services.SignInService;
import ru.itis.springsemwork.services.UsersService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/signIn")
public class SignInController {

    private final UsersService usersService;

    @GetMapping
    public String getSignInPage(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("user", usersService.findByEmail(authentication.getName()));
            System.out.println(model.getAttribute("user"));
            return "redirect:/profile";
        }
        return "signIn";
    }
}