package com.huudan.playground.sec09.service;

import com.huudan.playground.sec09.dto.ProductDto;
import com.huudan.playground.sec09.mapper.EntityDtoMapper;
import com.huudan.playground.sec09.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {

    private final ProductRepository repository;

    private final Sinks.Many<ProductDto> sink;

    public ProductService(ProductRepository repository, Sinks.Many<ProductDto> sink) {
        this.repository = repository;
        this.sink = sink;
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> mono) {
        return mono.map(EntityDtoMapper::toEntity)
                .flatMap(this.repository::save)
                .map(EntityDtoMapper::toDto)
                .doOnNext(this.sink::tryEmitNext);
    }

    public Flux<ProductDto> productStream() {
        return this.sink.asFlux();
    }

}
