package com.huudan.accountservice.event;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EventProducer {
    KafkaSender<String, String> sender;

    public Mono<String> send(String topic, String message) {
        return sender
                .send(Mono.just(SenderRecord.create(new ProducerRecord<>(topic, message), message)))
                .then()
                .thenReturn("OK");
    }
}
