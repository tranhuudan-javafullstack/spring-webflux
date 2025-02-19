package com.huudan.aggregator.dto;


import com.huudan.aggregator.domain.Ticker;

public record Holding(Ticker ticker,
                      Integer quantity) {
}
