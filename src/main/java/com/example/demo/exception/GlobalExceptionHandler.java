package com.example.demo.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex) {
        // 回傳有 message 欄位的 JSON 格式
        return ResponseEntity
            .status(500)
            .body(new ErrorMessage("寄送失敗：" + ex.getMessage()));
    }

    // 這是一個簡單的錯誤格式
    static class ErrorMessage {
        public String message;
        public ErrorMessage(String msg) { this.message = msg; }
    }
}
