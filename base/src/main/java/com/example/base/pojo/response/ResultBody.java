package com.example.base.pojo.response;

import java.io.Serializable;

/**
 * 响应
 */
public class ResultBody{

    private int code;

    private String message;

    private Object body;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getBody() {
        return body;
    }

    public static class Builder {
        // Required parameters  final不可变的
        private final int code;
        private final String message;

        // Optional parameters - initialized to default values
        private Object body;

        public Builder(ResultCode resultCode) {
            this.code = resultCode.getCode();
            this.message = resultCode.getMessage();
        }

        //builder设值方法返回builder本身，以便把调用链接起来
        public Builder body(Object val)
        { body = val;      return this; }

        public ResultBody build() {
            return new ResultBody(this);
        }
    }

    public ResultBody(Builder builder) {
        this.code = builder.code;
        this.message = builder.message;
        this.body = builder.body;
    }

    @Override
    public String toString() {
        return "ResultBody{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", body=" + body +
                '}';
    }

/*    public static void main(String[] args) {
        ResultBody resultBody = new Builder(ResultCode.SUCCESS)
                .body("hello").build();
    }*/
}
