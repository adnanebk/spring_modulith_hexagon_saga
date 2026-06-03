package com.example.demo.stock.infra.adapters.repo;

import com.example.demo.stock.infra.entities.ProductEntity;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class SearchProductSpecificationUtils {


    static Specification<ProductEntity> includeValue(String property, @Nullable String value) {
        return (root, query, cb) -> {
            if (StringUtils.hasText(value)) {
                String formattedValue = "%" + value.toLowerCase().trim() + "%";
                return cb.like(cb.lower(root.get(property)), formattedValue);
            }
            return cb.conjunction();
        };
    }

    static Specification<ProductEntity> equalsValue(String property, @Nullable String value) {
        return (root, query, cb) -> {
            if (StringUtils.hasText(value)) {
                return cb.equal(root.get(property), value);
            }
            return cb.conjunction();
        };
    }

    static <T extends Comparable<? super T>> Specification<ProductEntity> greaterThanOrEqual(String property, @Nullable T value) {
        return (root, query, cb) -> {
            if (value != null) {
                return cb.greaterThanOrEqualTo(root.get(property), value);
            }
            return cb.conjunction();
        };
    }

    static <T extends Comparable<? super T>> Specification<ProductEntity> lessThanOrEqual(String property, @Nullable T value) {
        return (root, query, cb) -> {
            if (value != null) {
                return cb.lessThanOrEqualTo(root.get(property), value);
            }
            return cb.conjunction();
        };
    }
}
