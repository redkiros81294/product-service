package com.ctbe.yared.productservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String slug,
        String categoryName, // flattened — client doesn't need the full Category object
        Long categoryId,
        LocalDateTime createdAt
) {}
