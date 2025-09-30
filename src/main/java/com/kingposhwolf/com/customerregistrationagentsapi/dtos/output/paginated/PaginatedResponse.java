package com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.paginated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {
    private List<T> data;
    private PaginationDTO pagination;

    public static <T> PaginatedResponse<T> of(List<T> data, Long totalCount, Integer page, Integer size) {
        return new PaginatedResponse<>(data, PaginationDTO.of(totalCount, page, size));
    }
}
