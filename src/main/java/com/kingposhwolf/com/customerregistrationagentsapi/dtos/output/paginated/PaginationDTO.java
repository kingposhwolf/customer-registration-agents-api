package com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.paginated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDTO {
    private Long totalCount;
    private Integer currentPage;
    private Integer pageSize;
    private Integer totalPages;
    private Integer previousPage;
    private Integer nextPage;

    public static PaginationDTO of(Long totalCount, Integer page, Integer size) {
        int totalPages = (int) Math.ceil((double) totalCount / size);
        Integer previousPage = page > 1 ? page - 1 : null;
        Integer nextPage = page < totalPages ? page + 1 : null;

        return new PaginationDTO(
                totalCount,
                page,
                size,
                totalPages,
                previousPage,
                nextPage
        );
    }
}