package com.huudan.profileservice.repository;

import com.huudan.profileservice.data.Profile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProfileRepository extends ReactiveCrudRepository<Profile,Long> {
    Mono<Profile> findByEmail(String email);
}
