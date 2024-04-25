package com.trandyol.productapi.llm.service;

import com.trandyol.productapi.llm.LlmInternalApi;
import com.trandyol.productapi.llm.entity.ProductDescription;
import com.trandyol.productapi.llm.exceptions.ResourceNotFoundException;
import com.trandyol.productapi.llm.feign.LlmFeignClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class LlmService implements LlmInternalApi {

    private final LlmFeignClient llmFeignClient;
    private final Environment environment;

    public ProductDescription getProductImageDescriptions(long productId, byte[] encodedImage) {

        Map<String, Object> headers = Map.of("Authorization", "Bearer " + environment.getProperty("HUGGINGFACE_API_KEY"));
        final var response = llmFeignClient.getImageTextDefinition(encodedImage, headers);
        final var productImageDescription = response.stream().findFirst().orElseThrow(() -> new ResourceNotFoundException(
                String.format("generated text not found for : %s", productId)));

        final var productDescription = new ProductDescription();
        productDescription.setProductId(productId);
        productDescription.setGeneratedText(productImageDescription.getGeneratedText());

        return productDescription;
    }
}
