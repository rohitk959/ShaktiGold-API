package com.rohitrk.shaktigold.expceptionHandler;

import lombok.Data;

@Data
public class ApplicationException extends RuntimeException {
    private int statusCode;
    private String description;
    private Throwable e;

    public ApplicationException(String msg) {
        super(msg);
    }

    public ApplicationException(String msg, Exception e) {
        super(msg, e);
        this.e = e;
    }

    public ApplicationException(int statusCode, String msg, String description, Throwable e) {
        super(msg, e);
        this.statusCode = statusCode;
        this.description = description;
        this.e = e;
    }
}
