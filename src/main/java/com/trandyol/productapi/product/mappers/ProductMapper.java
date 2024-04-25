package com.trandyol.productapi.product.mappers;

import com.trandyol.productapi.model.ProductDto;
import com.trandyol.productapi.product.entity.Product;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    com.trandyol.productapi.product.entity.Product toEntity(ProductDto productDto);

    ProductDto toDto(com.trandyol.productapi.product.entity.Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductDto productDto, @MappingTarget Product product);
}