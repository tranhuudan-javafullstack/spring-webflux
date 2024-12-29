package com.huudan.webfluxpatterns.sec04.service;

import com.huudan.webfluxpatterns.sec04.dto.OrchestrationRequestContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import jakarta.annotation.PostConstruct;

import java.util.List;


import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class OrderCancellationService {

    Sinks.Many<OrchestrationRequestContext> sink;
    Flux<OrchestrationRequestContext> flux;

    List<Orchestrator> orchestrators;

    public OrderCancellationService(List<Orchestrator> orchestrators) {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.flux = this.sink.asFlux().publishOn(Schedulers.boundedElastic());
        this.orchestrators = orchestrators;
    }

    @PostConstruct
    public void init() {
        orchestrators.forEach(o -> this.flux.subscribe(o.cancel()));
    }

    public void cancelOrder(OrchestrationRequestContext ctx) {
        this.sink.tryEmitNext(ctx);
    }

}
