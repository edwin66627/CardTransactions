package com.nexus.exception;

import com.nexus.constant.ExceptionMessage;
import com.nexus.entity.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {
    public static final String ERROR_PATH = "/error";
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, message), httpStatus);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= INTERNAL_SERVER_ERROR)
    public ResponseEntity<HttpResponse> handleInternalServerException(Exception exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, ExceptionMessage.INTERNAL_SERVER_ERROR_MSG);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value= METHOD_NOT_ALLOWED)
    public ResponseEntity<HttpResponse> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(ExceptionMessage.METHOD_NOT_ALLOWED_MSG, supportedMethod));
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value= BAD_REQUEST)
    public ResponseEntity<HttpResponse> handleNotFoundException(Exception exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value= BAD_REQUEST)
    public ResponseEntity<HttpResponse> handleIllegalArgumentException(Exception exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value= BAD_REQUEST)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
    @RequestMapping(ERROR_PATH)
    public ResponseEntity<HttpResponse> notFound404() {
        return createHttpResponse(NOT_FOUND, ExceptionMessage.NOT_FOUND_MSG);
    }


}
