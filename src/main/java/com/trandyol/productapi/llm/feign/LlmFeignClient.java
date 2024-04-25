package com.trandyol.productapi.llm.feign;

import com.trandyol.productapi.llm.config.FeignConfiguration;
import com.trandyol.productapi.llm.entity.ProductImageDescription;
import feign.HeaderMap;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@FeignClient(value = "LlmInternalApi", url = "https://api-inference.huggingface.co/models", configuration = FeignConfiguration.class)
public interface LlmFeignClient {

    @RequestLine("POST /Salesforce/blip-image-captioning-base")
    List<ProductImageDescription> getImageTextDefinition(byte[] image, @HeaderMap Map<String, Object> headers);
}