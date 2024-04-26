package com.trandyol.productapi.llm.feign;

import com.trandyol.productapi.llm.config.FeignConfiguration;
import com.trandyol.productapi.llm.entity.ProductImageDescription;
import com.trandyol.productapi.llm.entity.SelectionResponse;
import com.trandyol.productapi.llm.feign.model.LlmRequest;
import feign.HeaderMap;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@FeignClient(value = "LlmInternalApi", url = "https://api-inference.huggingface.co/models", configuration = FeignConfiguration.class)
public interface LlmFeignClient {

    @RequestLine("POST /{group}/{model}")
    List<ProductImageDescription> getImageTextDefinition(byte[] image,
                                                         @HeaderMap Map<String, Object> headers,
                                                         @Param("group") String group,
                                                         @Param("model") String model);

    @RequestLine("POST /{group}/{model}")
    List<SelectionResponse> getBestImageTextDefinition(LlmRequest request,
                                                       @HeaderMap Map<String, Object> headers,
                                                       @Param("group") String group,
                                                       @Param("model") String model);

}