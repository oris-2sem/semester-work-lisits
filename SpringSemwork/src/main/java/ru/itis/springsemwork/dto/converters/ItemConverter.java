package ru.itis.springsemwork.dto.converters;

import org.mapstruct.Mapper;
import ru.itis.springsemwork.dto.ItemDto;
import ru.itis.springsemwork.models.Item;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemConverter {
    ItemDto from(Item item);
    List<ItemDto> from(List<Item> items);
}
