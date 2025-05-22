package com.prime.quickcart.order_service_api.adviser;

import com.prime.quickcart.order_service_api.exception.EntryNotFoundException;
import com.prime.quickcart.order_service_api.util.StandardResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppWideExceptionHandler {
    @ExceptionHandler(EntryNotFoundException.class)
    public ResponseEntity<StandardResponseDTO> handleEntryNotFoundException(EntryNotFoundException e) {
        return new ResponseEntity<>(
                new StandardResponseDTO(
                        404, e.getMessage(),
                        e
                ), HttpStatus.NOT_FOUND
        );
    }
}
