package io.github.leo.topichub.exception;

import io.github.leo.topichub.dto.error.ApiError;
import io.github.leo.topichub.dto.error.ApiErrorResponse;
import io.github.leo.topichub.dto.error.ApiErrorValidation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

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

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiError> handleForbiddenException(ForbiddenException ex, HttpServletRequest request) {

        HttpStatus errorType = HttpStatus.FORBIDDEN;

        ApiError error = new ApiError(
                errorType.value(),
                errorType.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.status(errorType).body(error);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiError> handleInvalidPasswordException(
            InvalidPasswordException ex, HttpServletRequest request) {

        HttpStatus errorType = HttpStatus.BAD_REQUEST;

        ApiError error = new ApiError(
                errorType.value(),
                errorType.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.status(errorType).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<ApiErrorValidation> fields = ex.getFieldErrors().stream()
                .map(error -> new ApiErrorValidation(error.getField(), error.getDefaultMessage()))
                .toList();

        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation error", fields);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
