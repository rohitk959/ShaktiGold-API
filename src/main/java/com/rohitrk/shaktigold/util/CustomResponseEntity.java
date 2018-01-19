package com.rohitrk.shaktigold.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class CustomResponseEntity {
    public static ResponseEntity<?> ok(Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "SUCCESS");
        result.put("message", data);
        return ResponseEntity.ok(result);
    }

    public static ResponseEntity<?> forbidden() {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "SUCCESS");
        return new ResponseEntity<Object>(result, HttpStatus.FORBIDDEN);
    }
}
