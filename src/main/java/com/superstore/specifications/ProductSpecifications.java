package com.superstore.specifications;

import com.superstore.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecifications {
    public static Specification<Product> priceGreaterThanOrEqualTo(BigDecimal minPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> priceLessThanOrEqualTo(BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> hasDiscount(Boolean hasDiscount) {
        return (root, query, criteriaBuilder) -> {
            if (hasDiscount) {
                return criteriaBuilder.isNotNull(root.get("discountPrice"));
            } else {
                return criteriaBuilder.isNull(root.get("discountPrice"));
            }
        };
    }

    public static Specification<Product> categoryIdEquals(Long categoryId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category").get("categoryId"), categoryId);
    }
}
