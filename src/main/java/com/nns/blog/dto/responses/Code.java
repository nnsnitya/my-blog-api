package com.nns.blog.dto.responses;

public enum Code {
    SUCCESS(2, "Success"),
    FAILED(6, "Failed");

    private final int code;
    private final String message;

    Code(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() { return code; }
    public String getMessage() { return message; }

}
