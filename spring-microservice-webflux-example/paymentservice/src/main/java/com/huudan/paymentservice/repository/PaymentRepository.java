package com.huudan.paymentservice.repository;

import com.huudan.paymentservice.data.Payment;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PaymentRepository extends ReactiveCrudRepository<Payment,Long> {
    @Query("SELECT * FROM payment WHERE account_id = :id")
    Flux<Payment> findByAccountId(String id);
}
