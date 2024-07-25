package com.rebelo.springsecurityjwt.exception;

import com.rebelo.springsecurityjwt.exception.model.NotFoundException;
import com.rebelo.springsecurityjwt.exception.model.UnprocessableEntityException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exception) {
        LOGGER.error("Exception thrown:", exception);
        return parseExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalStateException(IllegalStateException exception) {
        LOGGER.error("IllegalStateException thrown:", exception);
        return parseExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        LOGGER.error("IllegalArgumentException thrown:", exception);
        return parseExceptionMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        LOGGER.error("ConstraintViolationException thrown:", exception);
        return parseExceptionMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception) {
        LOGGER.error("HttpMediaTypeNotSupportedException thrown:", exception);
        return parseExceptionMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        LOGGER.error("HttpMessageNotReadableException thrown:", exception);
        return parseExceptionMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        LOGGER.error("HttpRequestMethodNotSupportedException thrown:", exception);

        String errorMessage = exception.getMessage();
        var supportedHttpMethods = exception.getSupportedMethods();
        if (!ObjectUtils.isEmpty(supportedHttpMethods)) {
            errorMessage += ". Supported methods: " + String.join(", ", supportedHttpMethods);
        }

        return parseExceptionMessage(HttpStatus.METHOD_NOT_ALLOWED, errorMessage);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        LOGGER.error("MethodArgumentNotValidException thrown:", exception);

        String errorMessage = null;
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            errorMessage = fieldErrors.stream()
                    .map(FieldError::getDefaultMessage)
                    .toList()
                    .toString();
        }

        return parseExceptionMessage(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ExceptionResponse> handleTransactionSystemException(TransactionSystemException exception) {
        LOGGER.error("TransactionSystemException thrown:", exception);

        var errorMessage = "An error occurred during transaction processing";
        var rootCause = exception.getRootCause();
        if (rootCause != null && !ObjectUtils.isEmpty(rootCause.getMessage())) {
            errorMessage += ". Root cause: " + rootCause.getMessage();
        }

        return parseExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleAuthenticationException(AuthenticationException exception) {
        LOGGER.error("AuthenticationException thrown:", exception);
        return parseExceptionMessage(HttpStatus.UNAUTHORIZED, "Authentication failed: " + exception.getMessage());
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException exception) {
        LOGGER.error("AuthenticationCredentialsNotFoundException thrown:", exception);
        return parseExceptionMessage(HttpStatus.UNAUTHORIZED, "Authentication credentials not found: " + exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException exception) {
        LOGGER.error("NotFoundException thrown:", exception);
        return parseExceptionMessage(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ExceptionResponse> handleUnprocessableEntityException(UnprocessableEntityException exception) {
        LOGGER.error("UnprocessableEntityException thrown:", exception);
        return parseExceptionMessage(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage());
    }

    private ResponseEntity<ExceptionResponse> parseExceptionMessage(final HttpStatus httpStatus, final String message) {
        var errorHttpStatus = ObjectUtils.isEmpty(httpStatus) ? HttpStatus.INTERNAL_SERVER_ERROR : httpStatus;
        var errorMessage = ObjectUtils.isEmpty(message) ? "No defined message" : message;

        return ResponseEntity.status(httpStatus).body(new ExceptionResponse(errorHttpStatus, errorMessage, System.currentTimeMillis()));
    }
}
