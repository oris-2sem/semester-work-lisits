package ru.itis.springsemwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.springsemwork.config.CurrencyRateConfig;
import ru.itis.springsemwork.services.UsersService;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class RootController {

    private final UsersService usersService;

    @GetMapping
    public String getMainPage(Authentication authentication, Model model, HttpServletResponse response) {
        if (authentication != null) {
            model.addAttribute("user", usersService.findByEmail(authentication.getName()));
        }
        return "mainPage";
    }
}
