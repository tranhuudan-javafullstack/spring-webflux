package com.huudan.aggregator.dto;

import com.huudan.aggregator.domain.Ticker;
import com.huudan.aggregator.domain.TradeAction;

public record TradeRequest(Ticker ticker,
                           TradeAction action,
                           Integer quantity) {
}
