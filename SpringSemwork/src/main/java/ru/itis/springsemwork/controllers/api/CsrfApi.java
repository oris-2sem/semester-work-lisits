package ru.itis.springsemwork.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itis.springsemwork.dto.ExceptionDto;

import javax.servlet.http.HttpServletRequest;

@Tags(value = {
        @Tag(name = "Csrf")
})
public interface CsrfApi {

    @Operation(summary = "Get CSRF token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Now you can get CSRF token from response",
                    content = {
                            @Content(mediaType = "application/json")
                    }
            )
    })
    @GetMapping("/get-csrf-token")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> myEndpoint(HttpServletRequest request);
}
