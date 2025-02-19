package com.huudan.userservice.service;

import com.huudan.userservice.dto.TransactionRequestDto;
import com.huudan.userservice.dto.TransactionResponseDto;
import com.huudan.userservice.dto.TransactionStatus;
import com.huudan.userservice.entity.UserTransaction;
import com.huudan.userservice.repository.UserRepository;
import com.huudan.userservice.repository.UserTransactionRepository;
import com.huudan.userservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    private final UserRepository userRepository;

    private final UserTransactionRepository transactionRepository;

    public TransactionService(UserRepository userRepository, UserTransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto) {
        return this.userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                .filter(Boolean::booleanValue)
                .map(b -> EntityDtoUtil.toEntity(requestDto))
                .flatMap(this.transactionRepository::save)
                .map(ut -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
                .defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
    }

    public Flux<UserTransaction> getByUserId(int userId) {
        return this.transactionRepository.findByUserId(userId);
    }

}
