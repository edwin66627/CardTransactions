package com.nexus.utils;

import com.nexus.entity.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtility {
    public static ResponseEntity<HttpResponse> buildResponse(String message, HttpStatus httpStatus) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, message), httpStatus);
    }
}
