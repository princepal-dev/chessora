package org.princeworks.chessora.common;

import lombok.Data;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class CommonResponse<T> {
  public boolean status;
  public String message;
  private T data;
  private PaginationData pagination;
  private LocalDateTime timestamp;

  public static <T> CommonResponse<T> success(String message, T data, PaginationData pagination) {
    return new CommonResponse<>(true, message, data, pagination, LocalDateTime.now());
  }

  public static <T> CommonResponse<T> success(String message, T data) {
    return new CommonResponse<>(true, message, data, null, LocalDateTime.now());
  }

  public static <T> CommonResponse<T> success(String message) {
    return new CommonResponse<>(true, message, null, null, LocalDateTime.now());
  }

  public static <T> CommonResponse<T> error(String message) {
    return new CommonResponse<>(false, message, null, null, LocalDateTime.now());
  }

  public static <T> CommonResponse<T> error(String message, T data) {
    return new CommonResponse<>(false, message, data, null, LocalDateTime.now());
  }
}
