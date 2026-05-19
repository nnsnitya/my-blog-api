package com.nns.blog.exceptions;

import com.nns.blog.dto.responses.Code;
import com.nns.blog.dto.responses.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resoureceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        String msg = ex.getMessage();
        return ResponseHandler.generateResp(msg, HttpStatus.NOT_FOUND, null, Code.FAILED.getCode());
    }

}
