package com.nns.blog.exceptions;

import com.nns.blog.dto.responses.Code;
import com.nns.blog.dto.responses.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resoureceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        String msg = ex.getMessage();
        return ResponseHandler.generateResp(msg, HttpStatus.NOT_FOUND, null, Code.FAILED.getCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            resp.put(fieldName, message);
        });
        return ResponseHandler.generateResp("MethodArgsNotValidEx", HttpStatus.BAD_REQUEST, resp, Code.FAILED.getCode());
    }

}
