package com.huudan.playground.sec04.validator;

import com.huudan.playground.sec04.dto.CustomerDto;
import com.huudan.playground.sec04.exceptions.ApplicationExceptions;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {

    public static UnaryOperator<Mono<CustomerDto>> validate() {
        return mono -> mono.filter(hasName())
                           .switchIfEmpty(ApplicationExceptions.missingName())
                           .filter(hasValidEmail())
                           .switchIfEmpty(ApplicationExceptions.missingValidEmail());
    }

    private static Predicate<CustomerDto> hasName() {
        return dto -> Objects.nonNull(dto.name());
    }

    private static Predicate<CustomerDto> hasValidEmail() {
        return dto -> Objects.nonNull(dto.email()) && dto.email().contains("@");
    }

}
