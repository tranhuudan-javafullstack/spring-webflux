package com.huudan.webfluxpatterns.sec05.controller;

import com.huudan.webfluxpatterns.sec05.dto.ReservationItemRequest;
import com.huudan.webfluxpatterns.sec05.dto.ReservationResponse;
import com.huudan.webfluxpatterns.sec05.service.ReservationService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("sec05")
public class ReservationController {

      ReservationService service;

    @PostMapping("reserve")
    public Mono<ReservationResponse> reserve(@RequestBody Flux<ReservationItemRequest> flux){
        return this.service.reserve(flux);
    }

}
