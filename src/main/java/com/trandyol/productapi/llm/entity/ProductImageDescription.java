package com.trandyol.productapi.llm.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageDescription {

    @JsonProperty("generated_text")
    private String generatedText;
}
