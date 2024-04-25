package com.trandyol.productapi.llm.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LlmType {
    private List<LlmApiModel> models;
}
