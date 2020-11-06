package com.example.demo.infrastructure.exception;

/**
 * @author : HanMinyang
 * @date : 2020-10-19 16:41
 **/

public class ZookeeperException extends RuntimeException {
    private static final long serialVersionUID = 458968454565893456L;
    private final String msg;

    public String getMsg() {
        return msg;
    }

    public ZookeeperException(String msg) {
        this.msg = msg;
    }

    public ZookeeperException(String message, String msg) {
        super(message);
        this.msg = msg;
    }

    public ZookeeperException(String message, Throwable cause, String msg) {
        super(message, cause);
        this.msg = msg;
    }

    public ZookeeperException(Throwable cause, String msg) {
        super(cause);
        this.msg = msg;
    }

    public ZookeeperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String msg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.msg = msg;
    }
}
