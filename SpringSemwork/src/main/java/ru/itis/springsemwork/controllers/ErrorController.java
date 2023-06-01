package ru.itis.springsemwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.springsemwork.services.UsersService;

@Controller
@RequestMapping("/error")
@RequiredArgsConstructor
public class ErrorController {

    private final UsersService usersService;

    @GetMapping()
    public String getErrorPage(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("user", usersService.findByEmail(authentication.getName()));
        }
        return "error";
    }
}
