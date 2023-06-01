package ru.itis.springsemwork.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.springsemwork.dto.FileForm;

import javax.persistence.*;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "items")
public class Item {

    public enum State {
        ACTIVE,
        DELETED
    }

    public enum ItemType {
        coffee,
        tea,
        honey,
        dish
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private State state;

    private Integer weight;

    //maybe it should be enum
    private String country;

    @Enumerated(value = EnumType.STRING)
    private ItemType itemType;

    private Integer price;

    @ManyToMany(mappedBy = "itemId")
    private List<OrderItem> orders;

    @OneToMany(mappedBy = "item")
    private List<FileInfo> images;
}

