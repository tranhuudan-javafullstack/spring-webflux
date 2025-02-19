package com.huudan.customerportfolio.dto;

import com.huudan.customerportfolio.domain.Ticker;

public record Holding(Ticker ticker,
                      Integer quantity) {
}
