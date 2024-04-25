package com.trandyol.productapi.product.repository;

import com.trandyol.productapi.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}