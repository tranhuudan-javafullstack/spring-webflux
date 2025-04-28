package com.huudan.accountservice.service;

import com.huudan.accountservice.model.AccountDTO;
import com.huudan.accountservice.repository.AccountRepository;
import com.huudan.commonservice.common.CommonException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccountService {
    AccountRepository accountRepository;

    public Mono<AccountDTO> createNewAccount(AccountDTO accountDTO) {
        return Mono.just(accountDTO)
                .map(AccountDTO::dtoToEntity)
                .flatMap(accountRepository::save)
                .map(AccountDTO::entityToModel)
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    public Mono<AccountDTO> checkBalance(String id) {
        return findById(id);
    }

    public Mono<AccountDTO> findById(String id) {
        return accountRepository.findById(id)
                .map(AccountDTO::entityToModel)
                .switchIfEmpty(Mono.error(new CommonException("A01", "Account not found", HttpStatus.NOT_FOUND)));
    }

    public Mono<Boolean> bookAmount(double amount, String accountId) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new CommonException("A01", "Account not found", HttpStatus.NOT_FOUND)))
                .flatMap(account -> {
                    if (account.getBalance() < amount + account.getReserved()) {
                        return Mono.just(false);
                    }
                    account.setReserved(account.getReserved() + amount);
                    return accountRepository.save(account);
                })
                .flatMap(account -> Mono.just(true));
    }

    public Mono<AccountDTO> subtract(double amount, String accountId) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new CommonException("A01", "Account not found", HttpStatus.NOT_FOUND)))
                .flatMap(account -> {
                    account.setReserved(account.getReserved() - amount);
                    account.setBalance(account.getBalance() - amount);
                    return accountRepository.save(account);
                }).map(AccountDTO::entityToModel);
    }

    public Mono<AccountDTO> rollbackReserved(double amount, String accountId) {
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.error(new CommonException("A01", "Account not found", HttpStatus.NOT_FOUND)))
                .flatMap(account -> {
                    account.setReserved(account.getReserved() - amount);
                    return accountRepository.save(account);
                }).map(AccountDTO::entityToModel);
    }
}
