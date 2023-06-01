package ru.itis.springsemwork.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.springsemwork.controllers.api.ItemsApi;
import ru.itis.springsemwork.dto.ItemDto;
import ru.itis.springsemwork.services.ItemsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemsRestController implements ItemsApi {

    private final ItemsService itemsService;

    @Override
    public ResponseEntity<?> deleteItem(@RequestHeader("X-CSRF-TOKEN") String token, @PathVariable("item-id") Long itemId) {
        itemsService.deleteItem(itemId);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<ItemDto> saveItem(@RequestHeader("X-CSRF-TOKEN") String token, @RequestBody ItemDto item) {
        log.info("We are in rest item controller, try to save item");
        return ResponseEntity.ok()
                .body(itemsService.addNewItem(item));
    }

    @Override
    public ResponseEntity<ItemDto> updateItem(@RequestHeader("X-CSRF-TOKEN") String token, @RequestBody ItemDto item, @PathVariable("item-id") Long itemId) {
        log.info("Updating item " + itemId);
        return ResponseEntity.ok().body(itemsService.updateItem(item));
    }

    @Override
    public ResponseEntity<ItemDto> getItemBack(@RequestHeader("X-CSRF-TOKEN") String token, @PathVariable("item-id") Long itemId) {
        return ResponseEntity.ok().body(itemsService.doActive(itemId));
    }

    @Override
    public List<ItemDto> searchItem(@RequestParam("query") String query) {
        return itemsService.searchItems(query);
    }
}
