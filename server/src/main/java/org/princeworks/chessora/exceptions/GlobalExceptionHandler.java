package org.princeworks.chessora.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.princeworks.chessora.common.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<CommonResponse<Void>> handleRuntimeException(RuntimeException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponse.error(e.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<CommonResponse<Void>> handleRuntimeException(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(CommonResponse.error("Something went wrong!"));
  }
}
