package com.trandyol.productapi.product.mappers;

import com.trandyol.productapi.model.ProductDto;
import com.trandyol.productapi.model.ProductShortDto;
import com.trandyol.productapi.product.entity.ProductEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    ProductEntity toEntity(ProductDto productDto);

    ProductDto toDto(ProductEntity productEntity);

    ProductShortDto toShortDto(ProductEntity productEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductEntity partialUpdate(ProductDto productDto, @MappingTarget ProductEntity productEntity);
}