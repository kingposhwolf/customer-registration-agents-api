package com.kingposhwolf.com.customerregistrationagentsapi.common.services;

import com.kingposhwolf.com.customerregistrationagentsapi.common.dto.QueryParamsDTO;
import com.kingposhwolf.com.customerregistrationagentsapi.common.repositories.BaseRepository;
import com.kingposhwolf.com.customerregistrationagentsapi.dtos.output.paginated.PaginatedResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Getter
public abstract class BaseService<T, ID, CreateDTO, UpdateDTO, ListDTO, DetailDTO> {

    protected final BaseRepository<T, ID> repository;
    protected final String entityName;

    protected BaseService(BaseRepository<T, ID> repository, String entityName) {
        this.repository = repository;
        this.entityName = entityName;
    }

    public T create(CreateDTO createDTO) {
        beforeCreate(createDTO);
        T entity = mapCreateDTOToEntity(createDTO);
        return repository.save(entity);
    }

    public DetailDTO createAndMapToDetail(CreateDTO createDTO) {
        T entity = create(createDTO);
        return mapEntityToDetailDTO(entity);
    }

    public PaginatedResponse<T> findAll(QueryParamsDTO queryParams) {
        return findAll(queryParams, Map.of());
    }

    public PaginatedResponse<T> findAll(QueryParamsDTO queryParams, Map<String, Object> filters) {
        Page<T> page = repository.findAll(queryParams, filters);
        return PaginatedResponse.of(
                page.getContent(),
                page.getTotalElements(),
                queryParams.getPage(),
                queryParams.getPageSize()
        );
    }

    public <R> PaginatedResponse<R> findAll(QueryParamsDTO queryParams, Map<String, Object> filters, Function<T, R> mapper) {
        Page<T> page = repository.findAll(queryParams, filters);
        List<R> mappedData = page.getContent().stream().map(mapper).toList();

        return PaginatedResponse.of(
                mappedData,
                page.getTotalElements(),
                queryParams.getPage(),
                queryParams.getPageSize()
        );
    }

    public PaginatedResponse<ListDTO> findAllAsListDTO(QueryParamsDTO queryParams) {
        return findAllAsListDTO(queryParams, Map.of());
    }

    public PaginatedResponse<ListDTO> findAllAsListDTO(QueryParamsDTO queryParams, Map<String, Object> filters) {
        return findAll(queryParams, filters, this::mapEntityToListDTO);
    }

    public T findOne(ID id) {
        T entity = repository.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException(entityName + " with ID " + id + " not found");
        }
        return entity;
    }

    public ListDTO findOneAsListDTO(ID id) {
        T entity = findOne(id);
        return mapEntityToListDTO(entity);
    }

    public DetailDTO findOneAsDetailDTO(ID id) {
        T entity = findOne(id);
        log.warn("reach here========>");
        return mapEntityToDetailDTO(entity);
    }

    public List<T> findMany(List<ID> ids) {
        return repository.findByIds(ids);
    }

    public List<ListDTO> findManyAsListDTO(List<ID> ids) {
        List<T> entities = findMany(ids);
        return entities.stream().map(this::mapEntityToListDTO).toList();
    }

    public List<DetailDTO> findManyAsDetailDTO(List<ID> ids) {
        List<T> entities = findMany(ids);
        return entities.stream().map(this::mapEntityToDetailDTO).toList();
    }

    public T update(ID id, UpdateDTO updateDTO) {
        T entity = findOne(id);
        beforeUpdate(id, updateDTO, entity);
        mapUpdateDTOToEntity(updateDTO, entity);
        return repository.save(entity);
    }

    public DetailDTO updateAndMapToDetail(ID id, UpdateDTO updateDTO) {
        T entity = update(id, updateDTO);
        return mapEntityToDetailDTO(entity);
    }

    public ListDTO updateAndMapToList(ID id, UpdateDTO updateDTO) {
        T entity = update(id, updateDTO);
        return mapEntityToListDTO(entity);
    }

    public String delete(ID id) {
        T entity = findOne(id);
        beforeDelete(entity);
        repository.delete(entity);
        return getDeleteSuccessMessage(id);
    }

    public boolean exists(ID id) {
        return repository.existsById(id);
    }

    public List<T> bulkCreate(List<CreateDTO> createDTOs) {
        List<T> entities = createDTOs.stream()
                .map(this::mapCreateDTOToEntity)
                .toList();
        return repository.saveAll(entities);
    }

    public List<ListDTO> bulkCreateAndMapToList(List<CreateDTO> createDTOs) {
        List<T> entities = bulkCreate(createDTOs);
        return entities.stream().map(this::mapEntityToListDTO).toList();
    }

    public List<DetailDTO> bulkCreateAndMapToDetail(List<CreateDTO> createDTOs) {
        List<T> entities = bulkCreate(createDTOs);
        return entities.stream().map(this::mapEntityToDetailDTO).toList();
    }

    protected abstract T mapCreateDTOToEntity(CreateDTO createDTO);
    protected abstract void mapUpdateDTOToEntity(UpdateDTO updateDTO, T entity);
    protected abstract ListDTO mapEntityToListDTO(T entity);
    protected abstract DetailDTO mapEntityToDetailDTO(T entity);

    protected void beforeCreate(CreateDTO createDTO) {}
    protected void beforeUpdate(ID id, UpdateDTO updateDTO, T entity) {}
    protected void beforeDelete(T entity) {}

    protected String getDeleteSuccessMessage(ID id) {
        return entityName + " with ID " + id + " deleted successfully";
    }
}
