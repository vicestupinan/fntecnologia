package com.vmestupinan.products.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vmestupinan.products.exception.ProductAlreadyExistsException;
import com.vmestupinan.products.model.ApiError;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalHandlerException {

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ApiError> handleEntityNotFound(
                        EntityNotFoundException ex,
                        HttpServletRequest request) {

                log.error("[EntityNotFound] {} at {}", ex.getMessage(), request.getRequestURI());

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(ApiError.builder()
                                                .message(ex.getMessage())
                                                .status(HttpStatus.NOT_FOUND.value())
                                                .path(request.getRequestURI())
                                                .build());
        }

        @ExceptionHandler(ProductAlreadyExistsException.class)
        public ResponseEntity<ApiError> handleProductAlreadyExists(
                        ProductAlreadyExistsException ex,
                        HttpServletRequest request) {

                log.warn("[ProductAlreadyExists] {} at {}", ex.getMessage(), request.getRequestURI());

                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(ApiError.builder()
                                                .message(ex.getMessage())
                                                .status(HttpStatus.CONFLICT.value())
                                                .path(request.getRequestURI())
                                                .build());
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiError> handleValidationException(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {

                Map<String, List<String>> errors = new HashMap<>();

                ex.getBindingResult().getFieldErrors().forEach(error -> {
                        errors.computeIfAbsent(error.getField(), key -> new ArrayList<>())
                                        .add(error.getDefaultMessage());
                });

                log.warn("[Validation failed] Invalid request at {} - {}", request.getRequestURI(), errors);

                ApiError apiError = ApiError.builder()
                                .message("Validation failed")
                                .status(HttpStatus.BAD_REQUEST.value())
                                .path(request.getRequestURI())
                                .errors(errors)
                                .build();

                return ResponseEntity.badRequest().body(apiError);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiError> handleGeneralException(
                        Exception ex,
                        HttpServletRequest request) {

                log.error("[Unhandled exception] {} at {}", ex.getMessage(), request.getRequestURI(), ex);

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ApiError.builder()
                                                .message("An unexpected error occurred: " + ex.getMessage())
                                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                .path(request.getRequestURI())
                                                .build());
        }
}
