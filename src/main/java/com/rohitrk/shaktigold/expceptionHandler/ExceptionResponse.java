package com.rohitrk.shaktigold.expceptionHandler;

import lombok.Data;

import java.util.List;

@Data
public class ExceptionResponse {
    private final String result = "FAILURE";
    private int statusCode;
    private String errorMessage;
    private String errorDescription;
    private List<String> errors;
}
