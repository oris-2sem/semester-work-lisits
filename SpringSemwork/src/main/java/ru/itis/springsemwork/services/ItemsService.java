package ru.itis.springsemwork.services;

import ru.itis.springsemwork.dto.ItemDto;
import ru.itis.springsemwork.models.Item;
import ru.itis.springsemwork.models.Order;

import java.util.List;
import java.util.UUID;

public interface ItemsService {

    ItemDto getItemById(Long itemId);

    Long getNextitemId();
    ItemDto deleteItem(Long itemId);

    List<ItemDto> getAllItems();

    ItemDto addNewItem(ItemDto item);

    void addImage(ItemDto itemDto, Long imageId);

    List<ItemDto> searchItems(String query);

    List<ItemDto> getItemsByType(Item.ItemType itemType);

    ItemDto updateItem(ItemDto itemDto);

    List<ItemDto> getItemsForOrder(List<Order> orders);

    List<ItemDto> getDeletedItems();

    ItemDto doActive(Long itemId);
}
