package com.trandyol.productapi.model;

import com.trandyol.productapi.product.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for {@link ProductEntity}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String encodedImage;
    private String altText;
}
