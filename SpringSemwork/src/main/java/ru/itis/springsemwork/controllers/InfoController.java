package ru.itis.springsemwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.springsemwork.services.UsersService;

@Controller
@RequiredArgsConstructor
public class InfoController {

    private final UsersService usersService;

    @GetMapping("/delivery")
    public String getDeliveryPage(Model model, Authentication authentication) {
        checkAuthentication(authentication, model);
        return "delivery";
    }

    @GetMapping("/info")
    public String getCompanyInfoPage(Model model, Authentication authentication) {
        checkAuthentication(authentication, model);

        return "aboutCompany";
    }

    private void checkAuthentication(Authentication authentication, Model model) {
        if (authentication != null) {
            model.addAttribute("user", usersService.findByEmail(authentication.getName()));
        }
    }
}
