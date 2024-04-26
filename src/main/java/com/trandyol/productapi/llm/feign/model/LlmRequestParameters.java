package com.trandyol.productapi.llm.feign.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LlmRequestParameters {
    @JsonProperty("candidate_labels")
    private List<String> candidateLabels;
}
