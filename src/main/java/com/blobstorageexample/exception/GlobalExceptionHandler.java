package com.blobstorageexample.exception;

import com.blobstorageexample.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleResourceNotFoundException(ResourceNotFoundException exception) {
        ApiResponse response = new ApiResponse();
        response.setMessage(exception.getMessage());
        response.setStatus(false);
        return response;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleIllegalArgumentException(IllegalArgumentException exception) {
        ApiResponse response = new ApiResponse();
        response.setMessage(exception.getMessage());
        response.setStatus(false);
        return response;
    }

}
