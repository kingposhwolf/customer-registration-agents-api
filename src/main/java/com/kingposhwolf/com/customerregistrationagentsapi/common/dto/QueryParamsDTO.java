package com.kingposhwolf.com.customerregistrationagentsapi.common.dto;

import lombok.Data;

@Data
public class QueryParamsDTO {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String q;
    private String sortBy = "id";
    private String sortOrder = "desc";
}
