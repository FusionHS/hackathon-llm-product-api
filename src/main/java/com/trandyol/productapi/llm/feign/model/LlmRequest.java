package com.trandyol.productapi.llm.feign.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LlmRequest {
    private LlmRequestParameters parameters;
    private byte[] inputs;
}
