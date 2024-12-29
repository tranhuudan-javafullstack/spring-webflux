package com.huudan.webfluxpatterns.sec02.controller;

import com.huudan.webfluxpatterns.sec02.dto.FlightResult;
import com.huudan.webfluxpatterns.sec02.service.FlightSearchService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("sec02")
public class FlightsController {

    FlightSearchService service;

    @GetMapping(value = "flights/{from}/{to}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FlightResult> getFlights(@PathVariable String from, @PathVariable String to) {
        return this.service.getFlights(from, to);
    }

}
