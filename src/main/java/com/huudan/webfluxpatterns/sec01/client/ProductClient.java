package com.huudan.webfluxpatterns.sec01.client;

import com.huudan.webfluxpatterns.sec01.dto.ProductResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductClient {

    WebClient client;

    public ProductClient(@Value("${sec01.product.service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<ProductResponse> getProduct(Integer id) {
        return this.client
                .get()
                .uri("{id}", id)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .onErrorResume(ex -> Mono.empty());
    }

}
