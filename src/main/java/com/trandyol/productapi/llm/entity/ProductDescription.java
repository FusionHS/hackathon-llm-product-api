package com.trandyol.productapi.llm.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDescription {

    private Long productId;

    private String generatedText;
}
