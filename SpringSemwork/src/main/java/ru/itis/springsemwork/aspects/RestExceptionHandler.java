package ru.itis.springsemwork.aspects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.itis.springsemwork.dto.ExceptionDto;
import ru.itis.springsemwork.exceptions.NotFoundException;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handleNotFound(NotFoundException ex) {
        log.error("Not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionDto.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDto> handleAccessDenied(AccessDeniedException ex) {
        log.error("Access denied");
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ExceptionDto.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.FORBIDDEN.value())
                        .build());
    }
}
