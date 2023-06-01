package ru.itis.springsemwork.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.springsemwork.config.CurrencyRateConfig;
import ru.itis.springsemwork.dto.FileForm;
import ru.itis.springsemwork.dto.ItemDto;
import ru.itis.springsemwork.models.FileInfo;
import ru.itis.springsemwork.models.Item;
import ru.itis.springsemwork.models.User;
import ru.itis.springsemwork.services.FilesService;
import ru.itis.springsemwork.services.ItemsService;
import ru.itis.springsemwork.services.UsersService;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
@Slf4j
public class ItemsController {

//если мы хотим, чтобы к этой странице имел доступ только аутентифицированный пользователь
//    @PreAuthorize("isAuthenticated()")

    private final ItemsService itemsService;

    private final FilesService filesService;

    private final UsersService usersService;

    private final CurrencyRateConfig currencyRateConfig;

    @GetMapping
    public String getAllItemsPage(Model model, Authentication authentication) {
        checkAuthentication(authentication, model);
        List<ItemDto> items = itemsService.getAllItems();
        List<FileInfo> images = filesService.getImagesForItems(items);
        model.addAttribute("itemsList", items);
        model.addAttribute("images", images);
        model.addAttribute("currencies", currencyRateConfig.getRates());
        return "items";
    }

//    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{itemType}/{item-id}")
    public String getItemPage(@PathVariable("item-id") Long itemId, Model model, Authentication authentication, @PathVariable String itemType) {
        checkAuthentication(authentication, model);
        ItemDto item = itemsService.getItemById(itemId);
        List<ItemDto> itemToList = Collections.singletonList(item);
        List<FileInfo> images = filesService.getImagesForItems(itemToList);
        model.addAttribute("images", images);
        model.addAttribute("item", item);
        model.addAttribute("currencies", currencyRateConfig.getRates());
        return "item";
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add/{itemType}")
    public String getItemForm(@PathVariable("itemType") Item.ItemType itemType, Model model, Authentication authentication) {
        checkAuthentication(authentication, model);
        String type = itemType.toString();
        String templateName = "add" + type.substring(0, 1).toUpperCase() + type.substring(1);;
        log.info("Get page /add/" + templateName);
        return templateName;
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add")
    public String getForm(Model model, Authentication authentication) {
        checkAuthentication(authentication, model);
        model.addAttribute("itemDto", new ItemDto());
        log.info("Get page /add");
        return "addPage";
    }


    @GetMapping("/search")
    public String searchItems(@RequestParam("query") String query, Model model, Authentication authentication) {
        checkAuthentication(authentication, model);
        List<ItemDto> items = itemsService.searchItems(query);
        model.addAttribute("images", filesService.getImagesForItems(items));
        model.addAttribute("itemsList", items);
        model.addAttribute("currencies", currencyRateConfig.getRates());
        return "items";
    }


    @GetMapping("/{itemType}")
    public String getItemsByType(@PathVariable("itemType") Item.ItemType itemType, Model model, Authentication authentication) {
        checkAuthentication(authentication, model);
        List<ItemDto> items = itemsService.getItemsByType(itemType);
        List<FileInfo> images = filesService.getImagesForItems(items);
        model.addAttribute("itemsList", items);
        model.addAttribute("images", images);
        model.addAttribute("currencies", currencyRateConfig.getRates());
        return "items";
    }

     private void checkAuthentication(Authentication authentication, Model model) {
        if (authentication != null) {
            model.addAttribute("user", usersService.findByEmail(authentication.getName()));
        }
    }
}