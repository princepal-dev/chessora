package org.princeworks.chessora.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageResponse<T> {
    private T data;
    private PaginationData pagination;
}
