package ru.itis.springsemwork.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.springsemwork.dto.ExceptionDto;
import ru.itis.springsemwork.dto.ItemDto;
import ru.itis.springsemwork.models.Order;
import ru.itis.springsemwork.models.OrderItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Tags(value = {
        @Tag(name = "Orders")
})
@RequestMapping("/orders")
public interface OrdersApi {

    @Operation(summary = "Add item to cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item was added to cart and saved successfully",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ItemDto.class))
                    }
            )
    })
    @PostMapping("/addToCart")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<String> addItemToCart(@RequestHeader("X-CSRF-TOKEN") String token, @Parameter(description = "Items with its count") @RequestBody OrderItem orderItem, HttpServletRequest request, Authentication authentication);


    @Operation(summary = "Change order state to active - form order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Added and saved successfully",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ItemDto.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Order not found or other error",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))
                    }
            )
    })
    @PutMapping("/formOrder")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Order> formOrder(@RequestHeader("X-CSRF-TOKEN") String token, HttpServletRequest request, HttpServletResponse response, Authentication authentication);


    @Operation(summary = "Delete item from order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))
                    }
            )
    })
    @DeleteMapping("/deleteItem/{item-id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> deleteItemFromOrder(@RequestHeader("X-CSRF-TOKEN") String token, HttpServletRequest request, @Parameter(description = "item ID") @PathVariable("item-id") Long itemId);


}
