package com.huudan.paymentservice.service;

import com.google.gson.Gson;
import com.huudan.commonservice.common.CommonException;
import com.huudan.commonservice.model.AccountDTO;
import com.huudan.commonservice.utils.Constant;
import com.huudan.paymentservice.event.EventProducer;
import com.huudan.paymentservice.model.PaymentDTO;
import com.huudan.paymentservice.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService {
    PaymentRepository paymentRepository;

    WebClient webClientAccount;

    EventProducer eventProducer;

    Gson gson = new Gson();

    public Flux<PaymentDTO> getAllPayment(String id) {
        return paymentRepository.findByAccountId(id)
                .map(PaymentDTO::entityToDto)
                .switchIfEmpty(Mono.error(new CommonException("P02", "Account don't have payment", HttpStatus.NOT_FOUND)));
    }

    public Mono<PaymentDTO> makePayment(PaymentDTO paymentDTO) {
        return webClientAccount.get()
                .uri("/checkBalance/" + paymentDTO.getAccountId())
                .retrieve()
                .bodyToMono(AccountDTO.class)
                .flatMap(accountDTO -> {
                    if (paymentDTO.getAmount() <= accountDTO.getBalance()) {
                        paymentDTO.setStatus(Constant.STATUS_PAYMENT_CREATING);
                    } else {
                        throw new CommonException("P01", "Balance not enough", HttpStatus.BAD_REQUEST);
                    }
                    return createNewPayment(paymentDTO);
                });
    }

    public Mono<PaymentDTO> createNewPayment(PaymentDTO paymentDTO) {
        return Mono.just(paymentDTO)
                .map(PaymentDTO::dtoToEntity)
                .flatMap(paymentRepository::save)
                .map(PaymentDTO::entityToDto)
                .doOnError(throwable -> log.error(throwable.getMessage()))
                .publishOn(Schedulers.boundedElastic())
                .doOnSuccess(paymentDTO1 -> eventProducer.sendPaymentRequest(Constant.PAYMENT_REQUEST_TOPIC, gson.toJson(paymentDTO1)).subscribe());
    }

    public Mono<PaymentDTO> updateStatusPayment(PaymentDTO paymentDTO) {
        return paymentRepository.findById(paymentDTO.getId())
                .switchIfEmpty(Mono.error(new CommonException("P03", "Payment not found", HttpStatus.NOT_FOUND)))
                .flatMap(payment -> {
                    payment.setStatus(paymentDTO.getStatus());
                    return paymentRepository.save(payment);
                })
                .map(PaymentDTO::entityToDto);
    }
}
