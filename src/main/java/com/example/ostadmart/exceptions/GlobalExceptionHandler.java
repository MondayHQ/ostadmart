package com.example.ostadmart.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<AuthenticationFailedResponse> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        AuthenticationFailedResponse
                                .builder()
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .message("Bad credentials")
                                .build()
                );
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<AuthenticationFailedResponse> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        AuthenticationFailedResponse
                                .builder()
                                .status(HttpStatus.FORBIDDEN.value())
                                .message("Access Denied")
                                .build()
                );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleProductNotFoundException(ProductNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new CustomErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                e.getMessage()
                        )
                );
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<CustomErrorResponse> handleInsufficientStockException(InsufficientStockException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new CustomErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                e.getMessage()
                        )
                );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new CustomErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                e.getMessage()
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        String message = e.getBindingResult().getFieldErrors().getFirst().getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new CustomErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                message
                        )
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorResponse> handleJsonParseException(HttpMessageNotReadableException ex) {
        String message = "Malformed JSON request";

        if (ex.getCause() instanceof com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException target) {
            message = String.format("Unrecognized field '%s'. Please, check your request body.", target.getPropertyName());
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new CustomErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                message
                        )
                );
    }

//    @ExceptionHandler
//    public ResponseEntity<String> handleException(Exception exception) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
//    }

}
