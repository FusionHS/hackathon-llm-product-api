package com.trandyol.productapi.llm;

import com.trandyol.productapi.llm.config.FeignConfiguration;
import com.trandyol.productapi.llm.entity.ProductImageDescription;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FeignClient(value = "LlmInternalApi", url = "https://api-inference.huggingface.co/models", configuration = FeignConfiguration.class)
public interface LlmInternalApi {

    @RequestLine("POST /Salesforce/blip-image-captioning-base")
    List<ProductImageDescription> getImageTextDefinition(Byte[] image);
}
