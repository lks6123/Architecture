package com.leelu.library.http.exception;

/**
 * @author : leelu
 * CreateTime : 2018/8/3 11:34
 * Description : 统一处理服务器异常
 */
public class ServerException extends RuntimeException {
    private int code;
    private String message;

    public ServerException(int code, String msg) {
        super(msg);
        this.code = code;
        this.message = msg;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
