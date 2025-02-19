package com.huudan.commonservice.common;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class ValidateException extends RuntimeException {
    private final String code;
    private final Map<String, String> messageMap;
    private final HttpStatus status;

}
