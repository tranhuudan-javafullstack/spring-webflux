package com.huudan.orderservice.client;

import com.huudan.orderservice.dto.TransactionRequestDto;
import com.huudan.orderservice.dto.TransactionResponseDto;
import com.huudan.orderservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserClient {

    private final WebClient webClient;

    public UserClient(@Value("${user.service.url}") String url){
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<TransactionResponseDto> authorizeTransaction(TransactionRequestDto requestDto){
        return this.webClient
                .post()
                .uri("transaction")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(TransactionResponseDto.class);
    }

    public Flux<UserDto> getAllUsers(){
        return this.webClient
                .get()
                .uri("all")
                .retrieve()
                .bodyToFlux(UserDto.class);
    }


}
