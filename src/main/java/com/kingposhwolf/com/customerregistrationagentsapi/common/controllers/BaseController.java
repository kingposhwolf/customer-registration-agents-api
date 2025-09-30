package com.kingposhwolf.com.customerregistrationagentsapi.common.controllers;

import com.kingposhwolf.com.customerregistrationagentsapi.common.dto.QueryParamsDTO;
import com.kingposhwolf.com.customerregistrationagentsapi.common.services.BaseService;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.paginated.PaginatedResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Getter
public abstract class BaseController<T, ID, CreateDTO, UpdateDTO, ListDTO, DetailDTO, S extends BaseService<T, ID, CreateDTO, UpdateDTO, ListDTO, DetailDTO>> {

    protected final S service;

    protected BaseController(S service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<T> create(@RequestBody CreateDTO createDTO) {
        T entity = service.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<ListDTO>> findAll(@ModelAttribute QueryParamsDTO queryParams) {
        PaginatedResponse<ListDTO> response = service.findAllAsListDTO(queryParams, getCustomFilters(queryParams));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailDTO> findOne(@PathVariable ID id) {
        DetailDTO details = service.findOneAsDetailDTO(id);
        return ResponseEntity.ok(details);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable ID id, @RequestBody UpdateDTO updateDTO) {
        T entity = service.update(id, updateDTO);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable ID id) {
        String message = service.delete(id);
        return ResponseEntity.ok(message);
    }

    protected Map<String, Object> getCustomFilters(QueryParamsDTO queryParams) {
        return Map.of();
    }
}