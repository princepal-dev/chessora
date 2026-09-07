package org.princeworks.chessora.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommonResponse<T> {
    public boolean status;
    public String message;
    private T data;
    private LocalDateTime timestamp;
    
    public static <T> CommonResponse<T> success (String message, T data) {
        return new CommonResponse<>(true, message, data, LocalDateTime.now());
    }
    
    public static <T> CommonResponse<T> success (String message) {
        return new CommonResponse<>(true, message, null, LocalDateTime.now());
    }
    
    public static <T> CommonResponse<T> error (String message) {
        return new CommonResponse<>(false, message, null, LocalDateTime.now());
    }

    public static <T> CommonResponse<T> error (String message, T data) {
        return new CommonResponse<>(false, message, data, LocalDateTime.now());
    }
}
