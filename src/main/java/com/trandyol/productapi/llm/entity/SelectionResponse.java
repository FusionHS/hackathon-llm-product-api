package com.trandyol.productapi.llm.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectionResponse {

    private String label;
    private float score;
}
