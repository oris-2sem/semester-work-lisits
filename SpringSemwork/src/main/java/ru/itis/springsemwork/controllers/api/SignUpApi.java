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
import ru.itis.springsemwork.dto.ItemDto;
import ru.itis.springsemwork.dto.SignUpForm;
import ru.itis.springsemwork.dto.UserDto;

@Tags(value = {
        @Tag(name = "Sign Up")
})
public interface SignUpApi {

    @Operation(summary = "Sign up new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User signed up successfully",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ItemDto.class))
                    }
            )
    })
    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<UserDto> signUpUser(@RequestHeader("X-CSRF-TOKEN") String token, @Parameter(description = "Sign up form") @RequestBody SignUpForm user);

}
