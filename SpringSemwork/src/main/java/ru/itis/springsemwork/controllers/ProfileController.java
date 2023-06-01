package ru.itis.springsemwork.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itis.springsemwork.config.CurrencyRateConfig;
import ru.itis.springsemwork.dto.ItemDto;
import ru.itis.springsemwork.dto.SignUpForm;
import ru.itis.springsemwork.models.Order;
import ru.itis.springsemwork.models.User;
import ru.itis.springsemwork.repositories.OrdersItemsRepository;
import ru.itis.springsemwork.services.ItemsService;
import ru.itis.springsemwork.services.OrdersService;
import ru.itis.springsemwork.services.UsersService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final UsersService usersService;

    private final OrdersService ordersService;

    private final OrdersItemsRepository bothRepository;

    private final ItemsService itemsService;

    private final CurrencyRateConfig currencyRateConfig;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String getProfilePage(Authentication authentication, Model model) {
        User user = usersService.findByEmail(authentication.getName());
        List<Order> orders = ordersService.getAllUserOrders(user);
        Long totalCount = usersService.getTotalCount(orders);
        usersService.checkDiscount(user, totalCount);
        List<ItemDto> items = itemsService.getItemsForOrder(orders);
        model.addAttribute("items", items);
        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("deletedItems", itemsService.getDeletedItems());
        model.addAttribute("currencies", currencyRateConfig.getRates());
        return "profile";
    }

    @GetMapping("/bucket")
    public String getBucketPage(Authentication authentication, Model model, HttpServletRequest request) {
        Order order = null;
        if (authentication != null) {
            User user = usersService.findByEmail(authentication.getName());
            order = ordersService.getLastUserOrder(user);
            model.addAttribute("user", user);
            log.info("Authentication is ok");
        }
        if (order == null) {
            UUID orderId = OrdersRestController.getOrderId(request);
            order = ordersService.getCookieOrder(orderId);
        }

        List<ItemDto> items = itemsService.getItemsForOrder(Collections.singletonList(order));
        model.addAttribute("order", order);
        model.addAttribute("items", items);
        model.addAttribute("currencies", currencyRateConfig.getRates());
        order.setItemsWithCount(bothRepository.findByOrderId(order.getId()));
        return "bucket";
    }

    @PutMapping("/changeData")
    public ResponseEntity<?> changeData(Authentication authentication, @RequestBody SignUpForm signUpForm, Model model) {
        User user = null;
        if (authentication != null) {
            user = usersService.findByEmail(authentication.getName());
            log.info("Authentication is ok");
        }
        usersService.updateData(user, signUpForm);
        return ResponseEntity.ok("Success update");
    }
}