package ru.itis.springsemwork.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    public enum State {
        CREATING,
        ACTIVE,
        CANCELED,
        EXECUTED
    }

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(value = EnumType.STRING)
    private State State;

    @OneToMany(mappedBy = "orderId")
//    @JoinTable(
//            name = "orders_items",
//            joinColumns = @JoinColumn(name = "order_id")
//    )
//    @Column(name = "order_id")
    private List<OrderItem> itemsWithCount;

    private Date date;

    private Long totalPrice;

}
