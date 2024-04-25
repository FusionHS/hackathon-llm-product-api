package com.trandyol.productapi.llm.config;

import com.trandyol.productapi.llm.exceptions.ClientApiInternalErrorException;
import com.trandyol.productapi.llm.exceptions.ClientResourceNotFoundErrorException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@AllArgsConstructor
public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String method, Response response) {
        final var responseStatus = HttpStatus.valueOf(response.status());

        if (responseStatus.is5xxServerError()) {
            return new ClientApiInternalErrorException(response.reason());
        }

        if (responseStatus == HttpStatus.NOT_FOUND) {
            return new ClientResourceNotFoundErrorException(response.reason());
        }

        return defaultDecoder.decode(method, response);
    }
}
