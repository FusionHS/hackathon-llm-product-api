package com.trandyol.productapi.product.rest.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequest {

    private String name;
    private String description;
    private BigDecimal price;
    private MultipartFile file;
}
