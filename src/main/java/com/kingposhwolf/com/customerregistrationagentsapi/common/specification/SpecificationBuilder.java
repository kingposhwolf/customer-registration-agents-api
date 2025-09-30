package com.kingposhwolf.com.customerregistrationagentsapi.common.specification;

import com.kingposhwolf.com.customerregistrationagentsapi.common.dto.QueryParamsDTO;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpecificationBuilder<T> {

    private final List<String> searchFields;
    private final Map<String, String> customFilters;

    public SpecificationBuilder(List<String> searchFields, Map<String, String> customFilters) {
        this.searchFields = searchFields != null ? searchFields : new ArrayList<>();
        this.customFilters = customFilters != null ? customFilters : Map.of();
    }

    public Specification<T> build(QueryParamsDTO queryParams, Map<String, Object> filterValues) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (queryParams.getQ() != null && !queryParams.getQ().trim().isEmpty() && !searchFields.isEmpty()) {
                List<Predicate> searchPredicates = new ArrayList<>();
                String searchTerm = "%" + queryParams.getQ().toLowerCase() + "%";

                for (String field : searchFields) {
                    if (field.contains(".")) {
                        searchPredicates.add(criteriaBuilder.like(
                                criteriaBuilder.lower(getNestedPath(root, field).as(String.class)),
                                searchTerm
                        ));
                    } else {
                        searchPredicates.add(criteriaBuilder.like(
                                criteriaBuilder.lower(root.get(field).as(String.class)),
                                searchTerm
                        ));
                    }
                }

                predicates.add(criteriaBuilder.or(searchPredicates.toArray(new Predicate[0])));
            }

            for (Map.Entry<String, String> entry : customFilters.entrySet()) {
                String paramName = entry.getKey();
                String entityField = entry.getValue();
                Object value = filterValues.get(paramName);

                if (value != null && !value.toString().trim().isEmpty()) {
                    if (entityField.contains(".")) {
                        predicates.add(criteriaBuilder.equal(getNestedPath(root, entityField), value));
                    } else {
                        predicates.add(criteriaBuilder.equal(root.get(entityField), value));
                    }
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Path<?> getNestedPath(Root<T> root, String fieldPath) {
        String[] parts = fieldPath.split("\\.");
        Path<?> path = root;

        for (int i = 0; i < parts.length - 1; i++) {
            path = ((From<?, ?>) path).join(parts[i], JoinType.LEFT);
        }

        return path.get(parts[parts.length - 1]);
    }
}