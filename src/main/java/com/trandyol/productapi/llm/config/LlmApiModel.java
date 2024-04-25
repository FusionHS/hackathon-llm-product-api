package com.trandyol.productapi.llm.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LlmApiModel {
    private String group;
    private String model;
}
