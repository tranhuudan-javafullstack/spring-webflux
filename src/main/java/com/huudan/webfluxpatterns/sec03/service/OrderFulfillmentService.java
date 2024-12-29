package com.huudan.webfluxpatterns.sec03.service;

import com.huudan.webfluxpatterns.sec03.dto.OrchestrationRequestContext;
import com.huudan.webfluxpatterns.sec03.dto.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class OrderFulfillmentService {

    List<Orchestrator> orchestrators;

    public Mono<OrchestrationRequestContext> placeOrder(OrchestrationRequestContext ctx) {
        var list = orchestrators.stream()
                .map(o -> o.create(ctx))
                .collect(Collectors.toList());
        return Mono.zip(list, a -> a[0])
                .cast(OrchestrationRequestContext.class)
                .doOnNext(this::updateStatus);
    }

    private void updateStatus(OrchestrationRequestContext ctx) {
        var allSuccess = this.orchestrators.stream().allMatch(o -> o.isSuccess().test(ctx));
        var status = allSuccess ? Status.SUCCESS : Status.FAILED;
        ctx.setStatus(status);
    }

}
