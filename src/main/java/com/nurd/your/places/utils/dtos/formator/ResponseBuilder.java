package com.nurd.your.places.utils.dtos.formator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {
    public static <T> ResponseEntity<?> renderJSON(T data, String message, HttpStatus httpStatus) {
        ResponseFormat<T> response = ResponseFormat.<T>builder()
                .message(message)
                .status(httpStatus.getReasonPhrase())
                .data(data)
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }

    public static <T> ResponseEntity<?> renderError(T message, HttpStatus httpStatus) {
        ErrorResponseFormat<String> response = ErrorResponseFormat.<String>builder()
                .error( message.toString().toLowerCase())
                .status(httpStatus.getReasonPhrase())
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }

}
