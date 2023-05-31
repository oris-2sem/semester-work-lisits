package ru.itis.springsemwork.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.springsemwork.dto.ItemDto;
import ru.itis.springsemwork.dto.converters.ItemConverter;
import ru.itis.springsemwork.dto.converters.UserConverter;
import ru.itis.springsemwork.exceptions.NotFoundException;
import ru.itis.springsemwork.models.Item;
import ru.itis.springsemwork.models.Order;
import ru.itis.springsemwork.repositories.ItemsRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class ItemsServiceImpl implements ItemsService {

    private final ItemsRepository itemsRepository;

    private final ItemConverter itemConverter;

    @Override
    public ItemDto getItemById(Long itemId) {
        return itemConverter.from(itemsRepository.getById(itemId));
    }

    @Override
    public Long getNextitemId() {
        return itemsRepository.getLastItemIndex();
    }

    @Override
    public ItemDto deleteItem(Long itemId) {
        Item item = itemsRepository.getById(itemId);
        item.setState(Item.State.DELETED);
        itemsRepository.save(item);
        return itemConverter.from(item);
    }

    @Override
    public List<ItemDto> getAllItems() {
        return itemConverter.from(itemsRepository.findAllByState(Item.State.ACTIVE));
    }

    @Override
    public ItemDto addNewItem(ItemDto itemDto) {
        Item item = Item.builder()
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .country(itemDto.getCountry())
                .state(Item.State.ACTIVE)
                .itemType(itemDto.getItemType())
                .price(itemDto.getPrice())
                .build();
        if (itemDto.getWeight() != null) {
            item.setWeight(itemDto.getWeight());
        }
//        System.out.println(itemDto.getWeight());
        itemsRepository.save(item);
//        filesService.
        return itemConverter.from(item);
    }

    @Override
    public void addImage(ItemDto itemDto, Long imageId) {
        Item item = itemsRepository.getById(itemDto.getId());
        itemsRepository.save(item);
    }

    @Override
    public List<ItemDto> searchItems(String query) {
        if (query == null || query.equals("")) {
            return Collections.emptyList();
        }
        return itemConverter.from(itemsRepository.findByNameContainingIgnoreCase(query));
    }

    @Override
    public List<ItemDto> getItemsByType(Item.ItemType itemType) {
        return itemConverter.from(itemsRepository.getAllByItemTypeAndState(itemType, Item.State.ACTIVE));
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto) {
        Item item = itemsRepository.findById(itemDto.getId()).orElseThrow(() -> new NotFoundException("item not found"));
        if (itemDto.getName().length() > 0) {
            item.setName(itemDto.getName());
            log.info("Name updated");
        }
        if (itemDto.getDescription().length() > 0) {
            item.setDescription(itemDto.getDescription());
            log.info("Description updated");
        }
        if (itemDto.getWeight() != null) {
            item.setWeight(itemDto.getWeight());
            log.info("Weight updated");
        }
        if (itemDto.getPrice() != null) {
            item.setPrice(itemDto.getPrice());
            log.info("Price updated");
        }
        return itemConverter.from(itemsRepository.save(item));
    }

    @Override
    public List<ItemDto> getItemsForOrder(List<Order> orders) {
        List<Long> ids = new ArrayList<>();
        orders.forEach(order -> {
            if (order.getItemsWithCount() != null) {
                order.getItemsWithCount().forEach(orderItem -> ids.add(orderItem.getItemId()));
            }
        });
        if (ids.size() != 0) {
            return itemConverter.from(itemsRepository.findAllFromOrderItem(ids));
        }
        return null;
    }

    @Override
    public List<ItemDto> getDeletedItems() {
        return itemConverter.from(itemsRepository.findAllByState(Item.State.DELETED));
    }

    @Override
    public ItemDto doActive(Long itemId) {
        Item item = itemsRepository.getById(itemId);
        item.setState(Item.State.ACTIVE);
        itemsRepository.save(item);
        return itemConverter.from(item);
    }
}
