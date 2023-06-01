package ru.itis.springsemwork.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.itis.springsemwork.dto.UserDto;
import ru.itis.springsemwork.models.Order;
import ru.itis.springsemwork.models.OrderItem;
import ru.itis.springsemwork.models.User;
import ru.itis.springsemwork.repositories.ItemsRepository;
import ru.itis.springsemwork.repositories.OrdersItemsRepository;
import ru.itis.springsemwork.repositories.OrdersRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static ru.itis.springsemwork.models.Order.State.CREATING;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository ordersRepository;

    private final OrdersItemsRepository bothRepository;

    private final UsersService usersService;

    @Override
    public List<Order> getAllUserOrders(User user) {
        List<Order> orders = ordersRepository.findByUserIdOrderByDateDesc(user.getId());
        orders.forEach(this::setItemsWithCount);
        return orders;
    }

    @Override
    public void setItemsWithCount(Order order) {
        order.setItemsWithCount(new ArrayList<>(bothRepository.findByOrderId(order.getId())));
    }

    @Override
    public Order getLastUserOrder(User user) {
        return ordersRepository.findUsersTemplateOrder(user.getId());
    }

    @Override
    public List<OrderItem> addItems(OrderItem orderItem, Long userId) {
        Order order = ordersRepository.findById(orderItem.getOrderId());
        if (order != null) {
            log.info("Order was found, add items there");
            List<OrderItem> items = order.getItemsWithCount();
            System.out.println(items);
            boolean checkEqual = true;
            for (OrderItem item : items) {
                if (item.getItemId() == orderItem.getItemId()) {
                    Long newCount = item.getItemCount() + orderItem.getItemCount();
                    item.setItemCount(newCount);
                    bothRepository.save(item);
                    checkEqual = false;
                    log.info("next orderitem was saved");
                }
            }
            if (checkEqual) {
                items.add(orderItem);
                bothRepository.save(orderItem);
            }
            order.setItemsWithCount(items);
        } else {
            log.info("Current order not found, create new order with cookie-id");
            order = Order.builder()
                    .id(orderItem.getOrderId())
                    .State(CREATING)
                    .itemsWithCount(Collections.singletonList(orderItem))
                    .build();
            bothRepository.save(orderItem);
        }
        Long[] total = {0L};
        order.getItemsWithCount().forEach(orderItem1 -> {
            total[0] += (orderItem1.getItemCount() * orderItem1.getItemCost());
        });
        order.setTotalPrice(total[0]);
        if (userId != null) {
            order.setUserId(userId);
        }
        ordersRepository.save(order);
        return bothRepository.findByOrderId(orderItem.getOrderId());
    }

    @Override
    public Order formOrder(Long userId, UUID orderId, HttpServletRequest request, HttpServletResponse response) {
        Order order1 = ordersRepository.findById(orderId);
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        final Long[] total = {0L};
        order1.getItemsWithCount().forEach(orderItem -> total[0] += orderItem.getItemCount() * orderItem.getItemCost());
        UserDto user = usersService.findById(userId);
        Order order = Order.builder()
                .id(orderId)
                .date(Date.valueOf(localDateTime.toLocalDate()))
                .userId(userId)
                .State(Order.State.ACTIVE)
                .totalPrice((long) (total[0] * (1 - user.getPersonalDiscount() * 0.01)))
                .build();
        ordersRepository.save(order);
        deleteCookie(request, response);
        return order;
    }

    @Override
    public Order getCookieOrder(UUID orderId) {
        Order order = ordersRepository.findById(orderId);
        if (order == null) {
            order = Order.builder()
                    .id(orderId)
                    .State(CREATING)
                    .build();
        }
        ordersRepository.save(order);
        return order;
    }

    @Override
    public void deleteItem(UUID orderId, Long itemId) {
        Order order = ordersRepository.findById(orderId);
        List<OrderItem> items = order.getItemsWithCount();
        final OrderItem[] del = {null};
        items.forEach(orderItem -> {
            if (orderItem.getItemId() == itemId) {
                del[0] = orderItem;
                order.setTotalPrice(order.getTotalPrice()-(orderItem.getItemCount()*orderItem.getItemCost()));
            }
        });
        items.remove(del[0]);
        order.setItemsWithCount(items);
        ordersRepository.save(order);
        bothRepository.delete(del[0]);
    }

    private void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cartCookie")) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    log.info("cookie cartCookie was deleted");
                }
            }
        }
    }
}