package com.huudan.webfluxpatterns.sec04.service;

import com.huudan.webfluxpatterns.sec04.client.UserClient;
import com.huudan.webfluxpatterns.sec04.dto.OrchestrationRequestContext;
import com.huudan.webfluxpatterns.sec04.dto.Status;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class PaymentOrchestrator extends Orchestrator {

     UserClient client;

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext ctx) {
        return this.client.deduct(ctx.getPaymentRequest())
                .doOnNext(ctx::setPaymentResponse)
                .thenReturn(ctx)
                .handle(this.statusHandler());
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return ctx -> Objects.nonNull(ctx.getPaymentResponse()) && Status.SUCCESS.equals(ctx.getPaymentResponse().getStatus());
    }

    @Override
    public Consumer<OrchestrationRequestContext> cancel() {
        return ctx -> Mono.just(ctx)
                .filter(isSuccess())
                .map(OrchestrationRequestContext::getPaymentRequest)
                .flatMap(this.client::refund)
                .subscribe();
    }

}
