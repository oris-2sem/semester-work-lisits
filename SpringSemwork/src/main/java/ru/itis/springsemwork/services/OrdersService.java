package ru.itis.springsemwork.services;

import ru.itis.springsemwork.dto.OrderDto;
import ru.itis.springsemwork.models.Item;
import ru.itis.springsemwork.models.Order;
import ru.itis.springsemwork.models.OrderItem;
import ru.itis.springsemwork.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface OrdersService {
    List<Order> getAllUserOrders(User user);
    void setItemsWithCount(Order order);
    Order getLastUserOrder(User user);
    List<OrderItem> addItems(OrderItem orderItem, Long userId);
    Order formOrder(Long userId, UUID orderId, HttpServletRequest request, HttpServletResponse response);
    Order getCookieOrder(UUID orderId);

    void deleteItem(UUID orderId, Long itemId);
}
