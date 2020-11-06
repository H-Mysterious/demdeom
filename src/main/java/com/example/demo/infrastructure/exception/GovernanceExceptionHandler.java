package com.example.demo.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author : HanMinyang
 * @date : 2020-10-19 16:46
 **/

@Slf4j
@RestControllerAdvice
public class GovernanceExceptionHandler {
    @ExceptionHandler(value = ZookeeperException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> handleBusinessException(ZookeeperException e) {
        return ResponseEntity.ok(e.getMsg());
    }
}
