package com.trandyol.productapi.product.repository;

import com.trandyol.productapi.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}