package com.huudan.aggregator.dto;

import com.huudan.aggregator.domain.Ticker;

public record StockPriceResponse(Ticker ticker,
                                 Integer price) {
}
