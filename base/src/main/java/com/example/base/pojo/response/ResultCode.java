package com.example.base.pojo.response;

public enum ResultCode {

    SUCCESS(0, "请求成功"),
    ERROR(-1, "请求失败"),
    LACK_PARAM(-2,"接口传参错误");

    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code=code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
