package com.nns.blog.dto.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResp(String msg, HttpStatus status, Object responseObj, Integer code) {
        Map<String, Object> map =new HashMap<>();
        map.put("message", msg);
        map.put("status", status);
        map.put("data", responseObj);
        map.put("statusCode", code);
        return new ResponseEntity<>(map, status);
    }
}
