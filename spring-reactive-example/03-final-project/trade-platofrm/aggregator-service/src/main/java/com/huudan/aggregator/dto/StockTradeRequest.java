package com.huudan.aggregator.dto;


import com.huudan.aggregator.domain.Ticker;
import com.huudan.aggregator.domain.TradeAction;

public record StockTradeRequest(Ticker ticker,
                                Integer price,
                                Integer quantity,
                                TradeAction action) {

}
