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
import org.springframework.web.bind.annotation.*;
import ru.itis.springsemwork.dto.ExceptionDto;
import ru.itis.springsemwork.dto.ItemDto;

import java.util.List;

@Tags(value = {
        @Tag(name = "Items")
})
public interface ItemsApi {

    @Operation(summary = "Item deleting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))
                    }
            )
    })
    @DeleteMapping("/items/{item-id}/delete")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> deleteItem(
            @RequestHeader("X-CSRF-TOKEN") String token, @Parameter(description = "Item id", example = "1") @PathVariable("item-id") Long itemId);

    @Operation(summary = "Item saving")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Added and saved successfully",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ItemDto.class))
                    }
            )
    })
    @PostMapping("/items/save")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<ItemDto> saveItem(
            @RequestHeader("X-CSRF-TOKEN") String token, @Parameter(description = "Item") @RequestBody ItemDto item);


    @Operation(summary = "Items updating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Updated successfully",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ItemDto.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))
                    }
            )
    })
    @PutMapping("/items/{item-id}/update")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<ItemDto> updateItem(@RequestHeader("X-CSRF-TOKEN") String token, @RequestBody ItemDto item, @PathVariable("item-id") Long itemId);


    @Operation(summary = "Make item great again")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restored successfully",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ItemDto.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))
                    }
            )
    })
    @PutMapping("/items/{item-id}/back")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<ItemDto> getItemBack(@RequestHeader("X-CSRF-TOKEN") String token, @PathVariable("item-id") Long itemId);


    @Operation(summary = "Search items for context search")
    @GetMapping("/items/contextSearch")
    @ResponseStatus(HttpStatus.OK)
    List<ItemDto> searchItem(@RequestParam("query") String query);
}
