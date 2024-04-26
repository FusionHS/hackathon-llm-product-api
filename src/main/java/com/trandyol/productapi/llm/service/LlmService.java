package com.trandyol.productapi.llm.service;

import com.trandyol.productapi.llm.LlmInternalApi;
import com.trandyol.productapi.llm.config.LlmApiModel;
import com.trandyol.productapi.llm.config.LlmConfig;
import com.trandyol.productapi.llm.entity.ProductDescription;
import com.trandyol.productapi.llm.entity.SelectionResponse;
import com.trandyol.productapi.llm.exceptions.ResourceNotFoundException;
import com.trandyol.productapi.llm.feign.LlmFeignClient;
import com.trandyol.productapi.llm.feign.model.LlmRequest;
import com.trandyol.productapi.llm.feign.model.LlmRequestParameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LlmService implements LlmInternalApi {

    private final LlmConfig llmConfig;
    private final LlmFeignClient llmFeignClient;
    private final Environment environment;

    private static SelectionResponse getBestLabel(List<SelectionResponse> response) {
        return response.stream().sorted((o1, o2) -> o1.getScore() > o2.getScore() ? 1 : -1).findFirst().get();
    }

    public ProductDescription getProductImageDescriptions(long productId, byte[] encodedImage) {

        log.info("Available LLM Models: {}", llmConfig.getImageToText().toString());

        List<ProductDescription> productDescriptions = llmConfig.getImageToText().getModels().stream().parallel()
                .map(it -> getProductImageDescriptionsByModel(productId, encodedImage, it))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        return selectDescription(productDescriptions, encodedImage);
    }

    private ProductDescription selectDescription(List<ProductDescription> productDescriptions, byte[] encodedImage) {
        log.info(productDescriptions.toString());
        return getBestPromptByModel(productDescriptions, encodedImage, llmConfig.getPromptSelect().getModels().getFirst())
                .orElse(productDescriptions.getFirst());
    }

    private Optional<ProductDescription> getProductImageDescriptionsByModel(long productId, byte[] encodedImage, LlmApiModel model) {
        ProductDescription productDescription;
        try {
            Map<String, Object> headers = getHeaders();
            var response = llmFeignClient.getImageTextDefinition(encodedImage, headers, model.getGroup(), model.getModel());
            var productImageDescription = response.stream().findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("generated text not found for : %s", productId)));

            productDescription = ProductDescription.builder()
                    .productId(productId)
                    .generatedText(productImageDescription.getGeneratedText().replace("araffe", "a"))
                    .model(model)
                    .build();
        } catch (Exception exception) {
            log.error("Error getting from model: {}, {}", model, exception.getMessage());
            return Optional.empty();
        }

        return Optional.of(productDescription);
    }

    private Map<String, Object> getHeaders() {
        return Map.of("Authorization", "Bearer " + environment.getProperty("HUGGINGFACE_API_KEY"));
    }

    private Optional<ProductDescription> getBestPromptByModel(List<ProductDescription> productDescriptions, byte[] encodedImage, LlmApiModel model) {
        ProductDescription productDescription;
        try {
            List<String> inputList = productDescriptions.stream().map(ProductDescription::getGeneratedText).toList();
            Map<String, Object> headers = getHeaders();

            LlmRequest input = LlmRequest.builder().inputs(encodedImage).parameters(LlmRequestParameters.builder().candidateLabels(inputList).build()).build();

            var response = llmFeignClient.getBestImageTextDefinition(input, headers, model.getGroup(), model.getModel());

            productDescription = ProductDescription.builder()
                    .productId(productDescriptions.getLast().getProductId())
                    .generatedText(getBestLabel(response).getLabel())
                    .model(model)
                    .build();
        } catch (Exception exception) {
            log.error("Error getting from model: {}, {}", model, exception.getMessage());
            return Optional.empty();
        }

        log.info("Selection result: {}", productDescription);
        return Optional.of(productDescription);
    }

}
