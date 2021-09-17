package com.example.base.pojo.response;

public enum ResultCode {

    SUCCESS(0),
    ERROR(-1);

    private int code;

    ResultCode(int code) {
        this.code=code;
    }

    public int getCode() {
        return code;
    }
}
