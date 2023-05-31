package ru.itis.springsemwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.springsemwork.models.OrderItem;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrdersItemsRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(UUID orderId);

}
