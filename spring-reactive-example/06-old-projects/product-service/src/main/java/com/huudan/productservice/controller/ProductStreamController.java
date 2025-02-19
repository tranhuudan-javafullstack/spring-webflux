package com.huudan.productservice.controller;

import com.huudan.productservice.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("product")
public class ProductStreamController {

    private final Flux<ProductDto> flux;

    public ProductStreamController(Flux<ProductDto> flux) {
        this.flux = flux;
    }

    @GetMapping(value = "stream/{maxPrice}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductDto> getProductUpdates(@PathVariable int maxPrice){
        return this.flux
                    .filter(dto -> dto.getPrice() <= maxPrice);
    }

}
