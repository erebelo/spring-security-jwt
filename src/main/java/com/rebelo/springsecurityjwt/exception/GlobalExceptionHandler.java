package com.rebelo.springsecurityjwt.exception;

import com.rebelo.springsecurityjwt.exception.model.NotFoundException;
import com.rebelo.springsecurityjwt.exception.model.UnprocessableEntityException;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ExceptionResponse handleException(Exception exception) {
        log.error("Exception thrown:", exception);
        return parseExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ExceptionResponse handleIllegalStateException(IllegalStateException exception) {
        log.error("IllegalStateException thrown:", exception);
        return parseExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleIllegalArgumentException(IllegalArgumentException exception) {
        log.error("IllegalArgumentException thrown:", exception);
        return parseExceptionMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleConstraintViolationException(ConstraintViolationException exception) {
        log.error("ConstraintViolationException thrown:", exception);
        return parseExceptionMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public @ResponseBody ExceptionResponse handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException exception) {
        log.error("HttpMediaTypeNotSupportedException thrown:", exception);
        return parseExceptionMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception) {
        log.error("HttpMessageNotReadableException thrown:", exception);
        return parseExceptionMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public @ResponseBody ExceptionResponse handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception) {
        log.error("HttpRequestMethodNotSupportedException thrown:", exception);

        String errorMessage = exception.getMessage();
        String[] supportedHttpMethods = exception.getSupportedMethods();
        if (!ObjectUtils.isEmpty(supportedHttpMethods)) {
            errorMessage += ". Supported methods: " + String.join(", ", supportedHttpMethods);
        }

        return parseExceptionMessage(HttpStatus.METHOD_NOT_ALLOWED, errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ExceptionResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        log.error("MethodArgumentNotValidException thrown:", exception);

        String errorMessage = null;
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            errorMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).toList().toString();
        }

        return parseExceptionMessage(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @SuppressWarnings("ConstantConditions")
    @ExceptionHandler(TransactionSystemException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ExceptionResponse handleTransactionSystemException(TransactionSystemException exception) {
        log.error("TransactionSystemException thrown:", exception);

        String errorMessage = "An error occurred during transaction processing";
        Throwable rootCause = exception.getRootCause();
        if (rootCause != null && !ObjectUtils.isEmpty(rootCause.getMessage())) {
            errorMessage += ". Root cause: " + rootCause.getMessage();
        }

        return parseExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody ExceptionResponse handleAuthenticationException(AuthenticationException exception) {
        log.error("AuthenticationException thrown:", exception);
        return parseExceptionMessage(HttpStatus.UNAUTHORIZED, "Authentication failed: " + exception.getMessage());
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody ExceptionResponse handleAuthenticationCredentialsNotFoundException(
            AuthenticationCredentialsNotFoundException exception) {
        log.error("AuthenticationCredentialsNotFoundException thrown:", exception);
        return parseExceptionMessage(HttpStatus.UNAUTHORIZED,
                "Authentication credentials not found: " + exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ExceptionResponse handleNotFoundException(NotFoundException exception) {
        log.error("NotFoundException thrown:", exception);
        return parseExceptionMessage(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public @ResponseBody ExceptionResponse handleUnprocessableEntityException(UnprocessableEntityException exception) {
        log.error("UnprocessableEntityException thrown:", exception);
        return parseExceptionMessage(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage());
    }

    private ExceptionResponse parseExceptionMessage(final HttpStatus httpStatus, final String message) {
        String errorMessage = ObjectUtils.isEmpty(message) ? "No defined message" : message;
        return new ExceptionResponse(httpStatus, errorMessage, System.currentTimeMillis());
    }
}
