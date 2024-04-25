package com.trandyol.productapi.llm.config;

import feign.Contract;
import feign.Logger;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@AllArgsConstructor
public class FeignConfiguration {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient(new okhttp3.OkHttpClient.Builder().connectTimeout(Duration.ofMillis(3000))
                .readTimeout(Duration.ofMillis(3000)).build());
    }

    @Bean
    public Contract useFeignAnnotations() {
        return new Contract.Default();
    }
}
