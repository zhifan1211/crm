package com.example.demo.exception;

import com.example.demo.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 針對 NotFoundException，給 404 狀態碼
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(404, ex.getMessage()));
    }
    
    // 針對 AlreadyExistException，給 404 狀態碼
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ApiResponse<Void>> handleAlreadyExist(AlreadyExistException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(400, ex.getMessage()));
    }
    
    @ExceptionHandler(PointException.class)
    public ResponseEntity<ApiResponse<Void>> handlePointTypeException(PointException e) {
        return ResponseEntity
            .badRequest()
            .body(ApiResponse.error(400, e.getMessage()));
    }

    // 針對其他未知例外
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAllExceptions(Exception ex) {
        ex.printStackTrace(); // log 留下來
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(500, "系統錯誤：" + ex.getMessage()));
    }
}
