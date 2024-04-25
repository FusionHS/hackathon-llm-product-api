package com.trandyol.productapi.llm;

import com.trandyol.productapi.llm.entity.ProductDescription;

public interface LlmInternalApi {

    ProductDescription getProductImageDescriptions(long productId, byte[] encodedImage);
}
