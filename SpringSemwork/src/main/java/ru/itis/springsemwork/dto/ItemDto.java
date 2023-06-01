package ru.itis.springsemwork.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.springsemwork.models.FileInfo;
import ru.itis.springsemwork.models.Item;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {

    private Long id;

    private String name;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private Item.State state;

    private Integer weight;

    //maybe it should be enum
    private String country;

    @Enumerated(value = EnumType.STRING)
    private Item.ItemType itemType;

    private Integer price;


//    public static ItemDto from(Item item) {
//        return ItemDto.builder()
//                .id(item.getId())
//                .name(item.getName())
//                .description(item.getDescription())
//                .weight(item.getWeight())
//                .itemType(item.getItemType())
//                .state(item.getState())
//                .price(item.getPrice())
//                .build();
//    }
//
//    public static List<ItemDto> from(List<Item> items) {
//        return items.stream().map(ItemDto::from).collect(Collectors.toList());
//    }

}
