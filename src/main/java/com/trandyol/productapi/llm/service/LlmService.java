package com.trandyol.productapi.llm.service;

import com.trandyol.productapi.llm.LlmInternalApi;
import com.trandyol.productapi.llm.config.LlmApiModel;
import com.trandyol.productapi.llm.config.LlmConfig;
import com.trandyol.productapi.llm.entity.ProductDescription;
import com.trandyol.productapi.llm.exceptions.ResourceNotFoundException;
import com.trandyol.productapi.llm.feign.LlmFeignClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class LlmService implements LlmInternalApi {

    private final LlmConfig llmConfig;
    private final LlmFeignClient llmFeignClient;
    private final Environment environment;

    public ProductDescription getProductImageDescriptions(long productId, byte[] encodedImage) {

        List<ProductDescription> productDescriptions = llmConfig.getImageToText().getModels().stream().parallel()
                .map(it -> getProductImageDescriptionsByModel(productId, encodedImage, it))
                .toList();


        return selectDescription(productDescriptions);
    }

    private ProductDescription selectDescription(List<ProductDescription> productDescriptions) {

        log.info(productDescriptions.toString());

        return productDescriptions.getFirst();
    }

    private ProductDescription getProductImageDescriptionsByModel(long productId, byte[] encodedImage, LlmApiModel model) {

        Map<String, Object> headers = Map.of("Authorization", "Bearer " + environment.getProperty("HUGGINGFACE_API_KEY"));
        final var response = llmFeignClient.getImageTextDefinition(encodedImage, headers, model.getGroup(), model.getModel());
        final var productImageDescription = response.stream().findFirst().orElseThrow(() -> new ResourceNotFoundException(
                String.format("generated text not found for : %s", productId)));

        final var productDescription = ProductDescription.builder()
                .productId(productId)
                .generatedText(productImageDescription.getGeneratedText())
                .model(model)
                .build();

        return productDescription;
    }

}
