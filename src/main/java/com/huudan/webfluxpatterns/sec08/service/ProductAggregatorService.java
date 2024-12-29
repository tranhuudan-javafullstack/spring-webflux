package com.huudan.webfluxpatterns.sec08.service;

import com.huudan.webfluxpatterns.sec08.client.ProductClient;
import com.huudan.webfluxpatterns.sec08.client.ReviewClient;
import com.huudan.webfluxpatterns.sec08.dto.Product;
import com.huudan.webfluxpatterns.sec08.dto.ProductAggregate;
import com.huudan.webfluxpatterns.sec08.dto.Review;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class ProductAggregatorService {

      ProductClient productClient;

      ReviewClient reviewClient;

    public Mono<ProductAggregate> aggregate(Integer id){
        return Mono.zip(
               this.productClient.getProduct(id),
               this.reviewClient.getReviews(id)
        )
        .map(t -> toDto(t.getT1(), t.getT2()));
    }

    private ProductAggregate toDto(Product product, List<Review> reviews){
        return ProductAggregate.create(
                product.getId(),
                product.getCategory(),
                product.getDescription(),
                reviews
        );
    }


}
