package com.trandyol.productapi.llm.service;

import com.trandyol.productapi.llm.LlmInternalApi;
import com.trandyol.productapi.llm.entity.ProductDescription;
 import com.trandyol.productapi.llm.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class LlmService {

    private final LlmInternalApi llmInternalApi;

    public ProductDescription getProductImageDescriptions(Long productId, Byte[] encodedImage) {

        final var response = llmInternalApi.getImageTextDefinition(encodedImage);
        final var productImageDescription = response.stream().findFirst().orElseThrow(() -> new ResourceNotFoundException(
                String.format("generated text not found for : %s", productId)));

        final var productDescription = new ProductDescription();
        productDescription.setProductId(productId);
        productDescription.setGeneratedText(productImageDescription.getGeneratedText());

        return productDescription;
    }
}
