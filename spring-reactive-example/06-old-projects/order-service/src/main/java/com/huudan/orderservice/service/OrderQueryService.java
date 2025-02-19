package com.huudan.orderservice.service;

import com.huudan.orderservice.dto.PurchaseOrderResponseDto;
import com.huudan.orderservice.repository.PurchaseOrderRepository;
import com.huudan.orderservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderQueryService {

    private final PurchaseOrderRepository orderRepository;

    public OrderQueryService(PurchaseOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Flux<PurchaseOrderResponseDto> getProductsByUserId(int userId) {
        return Flux.fromStream(() -> this.orderRepository.findByUserId(userId).stream()) // blocking
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

}
