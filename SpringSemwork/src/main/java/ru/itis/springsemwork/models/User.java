package ru.itis.springsemwork.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public enum State {
        CONFIRMED, NOT_CONFIRMED, DELETED, BANNED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String email;


    @Column(name = "hashed_password")
    private String password;

    private String name;

    private Date birthdate;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Enumerated(value = EnumType.STRING)
    private State state;

//    @OneToOne()
//    @JoinColumn(name = "user_id")
//    private Token token;

    @OneToMany()
    @JoinColumn(name = "user_id")
    private List<Order> orders;

    @Column(name = "discount", columnDefinition = "int default 0")
    private Integer personalDiscount;
}
