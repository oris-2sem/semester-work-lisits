package ru.itis.springsemwork.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@Table(name = "orders_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_count")
    private Long itemCount;

    @Column(name = "item_cost")
    private Integer itemCost;

}
