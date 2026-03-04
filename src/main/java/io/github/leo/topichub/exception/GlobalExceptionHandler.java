package io.github.leo.topichub.exception;

import io.github.leo.topichub.dto.error.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflictException(ConflictException ex, HttpServletRequest request) {

        HttpStatus errorType = HttpStatus.CONFLICT;

        ApiError error = new ApiError(
                errorType.value(),
                errorType.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.status(errorType).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {

        HttpStatus errorType = HttpStatus.NOT_FOUND;

        ApiError error = new ApiError(
                errorType.value(),
                errorType.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.status(errorType).body(error);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(
            org.springframework.security.access.AccessDeniedException ex, HttpServletRequest request) {

        HttpStatus errorType = HttpStatus.FORBIDDEN;

        ApiError error = new ApiError(
                errorType.value(),
                errorType.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.status(errorType).body(error);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ApiError> handleUnprocessableEntityException(
            UnprocessableEntityException ex, HttpServletRequest request) {

        HttpStatus errorType = HttpStatus.UNPROCESSABLE_CONTENT;

        ApiError error = new ApiError(
                errorType.value(),
                errorType.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.unprocessableContent().body(error);
    }
}
