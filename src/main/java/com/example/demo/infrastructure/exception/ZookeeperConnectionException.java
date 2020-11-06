package com.example.demo.infrastructure.exception;

/**
 * 集群连接异常
 *
 * @author Luyouming
 */
public class ZookeeperConnectionException extends RuntimeException {

    public ZookeeperConnectionException() {
    }

    public ZookeeperConnectionException(String message) {
        super(message);
    }
}
