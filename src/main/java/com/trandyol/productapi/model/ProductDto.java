package com.trandyol.productapi.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO for {@link com.trandyol.productapi.product.entity.Product}
 */
@Data
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String encodedImage;
    private String altText;
}
