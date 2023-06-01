package ru.itis.springsemwork.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.springsemwork.controllers.api.OrdersApi;
import ru.itis.springsemwork.models.Order;
import ru.itis.springsemwork.models.OrderItem;
import ru.itis.springsemwork.models.User;
import ru.itis.springsemwork.services.OrdersService;
import ru.itis.springsemwork.services.UsersService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrdersRestController implements OrdersApi {

    private final OrdersService ordersService;

    private final UsersService usersService;

    @Override
    public ResponseEntity<String> addItemToCart(@RequestHeader("X-CSRF-TOKEN") String token, @RequestBody OrderItem orderItem, HttpServletRequest request, Authentication authentication) {
        log.info("We are in rest order controller, try to add item to cart");
        orderItem.setOrderId(UUID.fromString(getOrderId(request).toString()));
        if (authentication != null) {
            User user = usersService.findByEmail(authentication.getName());
            ordersService.addItems(orderItem, user.getId());
        } else {
            ordersService.addItems(orderItem, null);
        }
        return ResponseEntity.ok()
                .body("ok!");
    }

    @Override
    public ResponseEntity<Order> formOrder(@RequestHeader("X-CSRF-TOKEN") String token, HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        User user = null;
        if (authentication != null) {
            user = usersService.findByEmail(authentication.getName());
        }
        Order order = ordersService.formOrder(user.getId(), getOrderId(request), request, response);
        return ResponseEntity.ok().body(order);
    }

    @Override
    public ResponseEntity<?> deleteItemFromOrder(@RequestHeader("X-CSRF-TOKEN") String token, HttpServletRequest request, @PathVariable("item-id") Long itemId) {
        ordersService.deleteItem(getOrderId(request), itemId);
        return ResponseEntity.accepted().build();
    }


    static UUID getOrderId(HttpServletRequest request) {
        UUID orderId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cartCookie")) {
                    orderId = UUID.fromString(cookie.getValue());
                    break;
                }
            }
        }
        return orderId;
    }
}
