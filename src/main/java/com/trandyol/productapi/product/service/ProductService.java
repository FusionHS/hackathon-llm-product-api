package com.trandyol.productapi.product.service;

import com.trandyol.productapi.llm.LlmInternalApi;
import com.trandyol.productapi.llm.entity.ProductDescription;
import com.trandyol.productapi.model.ProductDto;
import com.trandyol.productapi.model.ProductShortDto;
import com.trandyol.productapi.product.entity.ProductEntity;
import com.trandyol.productapi.product.mappers.ProductMapper;
import com.trandyol.productapi.product.repository.ProductRepository;
import com.trandyol.productapi.product.rest.contract.ProductCreateRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final LlmInternalApi llmInternalApi;

    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProduct(Long id) {
        return productRepository.findById(id).map(productMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public ProductShortDto createProduct(ProductCreateRequest productCreateRequest) {

        String encodedImage;
        byte[] imageData;
        try {
            imageData = productCreateRequest.getFile().getBytes();
            encodedImage = new String(Base64.encodeBase64(imageData, true), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException("Error while converting the image data to bytes", e);
        }

        ProductDescription productImageDescriptions = llmInternalApi.getProductImageDescriptions(1, imageData);

        ProductDto productDto = ProductDto.builder()
                .name(productCreateRequest.getName())
                .price(productCreateRequest.getPrice())
                .description(productCreateRequest.getDescription())
                .encodedImage(encodedImage)
                .altText(productImageDescriptions.getGeneratedText())
                .build();

        ProductEntity productEntity = productRepository.save(productMapper.toEntity(productDto));
        return productMapper.toShortDto(productEntity);
    }

    public List<ProductShortDto> getProductsListings() {
        return productRepository.findAll().stream()
                .map(productMapper::toShortDto)
                .collect(Collectors.toList());
    }
}
