package com.huudan.webfluxpatterns.sec02.service;

import com.huudan.webfluxpatterns.sec02.client.DeltaClient;
import com.huudan.webfluxpatterns.sec02.client.FrontierClient;
import com.huudan.webfluxpatterns.sec02.client.JetBlueClient;
import com.huudan.webfluxpatterns.sec02.dto.FlightResult;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class FlightSearchService {

    DeltaClient deltaClient;

    FrontierClient frontierClient;
    JetBlueClient jetBlueClient;

    public Flux<FlightResult> getFlights(String from, String to) {
        return Flux.merge(
                        this.deltaClient.getFlights(from, to),
                        this.frontierClient.getFlights(from, to),
                        this.jetBlueClient.getFlights(from, to)
                )
                .take(Duration.ofSeconds(3));
    }

}
