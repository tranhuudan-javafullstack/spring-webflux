package com.huudan.webfluxpatterns.sec04.service;

import com.huudan.webfluxpatterns.sec04.client.ProductClient;
import com.huudan.webfluxpatterns.sec04.dto.OrchestrationRequestContext;
import com.huudan.webfluxpatterns.sec04.dto.Product;
import com.huudan.webfluxpatterns.sec04.dto.Status;
import com.huudan.webfluxpatterns.sec04.util.OrchestrationUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class OrderFulfillmentService {

    ProductClient productClient;

    PaymentOrchestrator paymentOrchestrator;

    InventoryOrchestrator inventoryOrchestrator;

    ShippingOrchestrator shippingOrchestrator;

    public Mono<OrchestrationRequestContext> placeOrder(OrchestrationRequestContext ctx) {
        return this.getProduct(ctx)
                .doOnNext(OrchestrationUtil::buildPaymentRequest)
                .flatMap(this.paymentOrchestrator::create)
                .doOnNext(OrchestrationUtil::buildInventoryRequest)
                .flatMap(this.inventoryOrchestrator::create)
                .doOnNext(OrchestrationUtil::buildShippingRequest)
                .flatMap(this.shippingOrchestrator::create)
                .doOnNext(c -> c.setStatus(Status.SUCCESS))
                .doOnError(ex -> ctx.setStatus(Status.FAILED))
                .onErrorReturn(ctx);
    }

    Mono<OrchestrationRequestContext> getProduct(OrchestrationRequestContext ctx) {
        return this.productClient.getProduct(ctx.getOrderRequest().getProductId())
                .map(Product::getPrice)
                .doOnNext(ctx::setProductPrice)
                .map(i -> ctx);
    }

}
