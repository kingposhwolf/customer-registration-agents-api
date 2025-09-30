package com.kingposhwolf.com.customerregistrationagentsapi.common.repositories;

import com.kingposhwolf.com.customerregistrationagentsapi.common.dto.QueryParamsDTO;
import com.kingposhwolf.com.customerregistrationagentsapi.common.specification.SpecificationBuilder;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
public abstract class BaseRepository<T, ID> {

    protected final JpaRepository<T, ID> repository;
    protected final JpaSpecificationExecutor<T> specificationExecutor;
    protected final List<String> searchFields;
    protected final Map<String, String> customFilters;
    protected final SpecificationBuilder<T> specificationBuilder;

    protected BaseRepository(
            JpaRepository<T, ID> repository,
            List<String> searchFields,
            Map<String, String> customFilters
    ) {
        this.repository = repository;
        this.specificationExecutor = (JpaSpecificationExecutor<T>) repository;
        this.searchFields = searchFields;
        this.customFilters = customFilters != null ? customFilters : new HashMap<>();
        this.specificationBuilder = new SpecificationBuilder<>(searchFields, this.customFilters);
    }

    public Page<T> findAll(QueryParamsDTO queryParams) {
        return findAll(queryParams, new HashMap<>());
    }

    public Page<T> findAll(QueryParamsDTO queryParams, Map<String, Object> filterValues) {
        Specification<T> spec = specificationBuilder.build(queryParams, filterValues);
        Pageable pageable = createPageable(queryParams);
        return specificationExecutor.findAll(spec, pageable);
    }

    public T findById(ID id) {
        log.warn("reach here========>", id);
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with ID " + id + " not found"));
    }

    public List<T> findByIds(List<ID> ids) {
        return repository.findAllById(ids);
    }

    public T save(T entity) {
        return repository.save(entity);
    }

    public List<T> saveAll(List<T> entities) {
        return repository.saveAll(entities);
    }

    public void delete(T entity) {
        repository.delete(entity);
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    public long count() {
        return repository.count();
    }

    protected Pageable createPageable(QueryParamsDTO queryParams) {
        int page = Math.max(0, queryParams.getPage() - 1);
        int size = Math.max(1, queryParams.getPageSize());
        Sort sort = createSort(queryParams);
        return PageRequest.of(page, size, sort);
    }

    protected Sort createSort(QueryParamsDTO queryParams) {
        String sortBy = queryParams.getSortBy() != null ? queryParams.getSortBy() : "id";
        Sort.Direction direction = "asc".equalsIgnoreCase(queryParams.getSortOrder())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        return Sort.by(direction, sortBy);
    }
}
