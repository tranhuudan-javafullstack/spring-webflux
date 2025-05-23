package com.huudan.profileservice.event;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
@Slf4j
public class EventProducer {
    private final KafkaSender<String, String> sender;

    public EventProducer(KafkaSender<String, String> sender) {
        this.sender = sender;
    }

    public Mono<String> send(String topic, String message) {
        return sender.send(Mono.just(SenderRecord.create(new ProducerRecord<>(topic, message), message)))
                .then()
                .thenReturn("OK");
    }
}
