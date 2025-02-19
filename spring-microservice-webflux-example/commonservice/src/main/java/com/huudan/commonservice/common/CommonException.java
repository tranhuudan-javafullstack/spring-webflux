package com.huudan.commonservice.common;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@RequiredArgsConstructor
public class CommonException extends RuntimeException {
    private final String code;
    private final String message;
    private final HttpStatus status;
}
