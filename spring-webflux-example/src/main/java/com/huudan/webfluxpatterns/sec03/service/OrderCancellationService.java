package com.huudan.webfluxpatterns.sec03.service;

import com.huudan.webfluxpatterns.sec03.dto.OrchestrationRequestContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import jakarta.annotation.PostConstruct;

import java.util.List;

@Service
public class OrderCancellationService {


    private Sinks.Many<OrchestrationRequestContext> sink;
    private Flux<OrchestrationRequestContext> flux;
    private final List<Orchestrator> orchestrators;

    // Constructor injection
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
