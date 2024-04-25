package com.trandyol.productapi.llm.entity;

import com.trandyol.productapi.llm.config.LlmApiModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDescription {

    private Long productId;
    private String generatedText;
    private LlmApiModel model;
}
