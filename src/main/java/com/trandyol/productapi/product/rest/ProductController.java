package com.trandyol.productapi.product.rest;

import com.trandyol.productapi.model.ProductDto;
import com.trandyol.productapi.product.rest.contract.ProductCreateRequest;
import com.trandyol.productapi.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProductsById(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductDto addProduct(@ModelAttribute ProductCreateRequest productCreateRequest) {
        return productService.createProduct(productCreateRequest);
    }

}
