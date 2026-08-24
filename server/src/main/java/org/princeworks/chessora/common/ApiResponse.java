package org.princeworks.chessora.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    public boolean status;
    public String message;
    private T data;
    
    public static <T> ApiResponse<T> success (String message, T data) {
        return new ApiResponse<>(true, message, data);
    }
    
    public static <T> ApiResponse<T> success (String message) {
        return new ApiResponse<>(true, message, null);
    }
    
    public static <T> ApiResponse<T> error (String message) {
        return new ApiResponse<>(false, message, null);
    }

    public static <T> ApiResponse<T> error (String message, T data) {
        return new ApiResponse<>(false, message, data);
    }
}
