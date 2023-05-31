package ru.itis.springsemwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.springsemwork.models.Order;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.State != 'CREATING' and o.userId = :userId order by o.date desc")
    List<Order> findByUserIdOrderByDateDesc(@Param("userId") Long userId);
    Order findById(UUID id);
    @Query("select distinct o from Order o where o.State = 'CREATING' and o.userId = :userId order by o.date desc")
    Order findUsersTemplateOrder(@Param("userId") Long userId);
}
