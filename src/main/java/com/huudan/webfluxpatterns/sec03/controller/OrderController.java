package com.huudan.webfluxpatterns.sec03.controller;

import com.huudan.webfluxpatterns.sec03.dto.OrderRequest;
import com.huudan.webfluxpatterns.sec03.dto.OrderResponse;
import com.huudan.webfluxpatterns.sec03.service.OrchestratorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("sec03")
public class OrderController {

      OrchestratorService service;

    @PostMapping("order")
    public Mono<ResponseEntity<OrderResponse>> placeOrder(@RequestBody Mono<OrderRequest> mono){
        return this.service.placeOrder(mono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
