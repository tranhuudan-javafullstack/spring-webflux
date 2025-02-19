package com.huudan.customerportfolio.dto;

import com.huudan.customerportfolio.domain.Ticker;
import com.huudan.customerportfolio.domain.TradeAction;

public record StockTradeResponse(Integer customerId,
                                 Ticker ticker,
                                 Integer price,
                                 Integer quantity,
                                 TradeAction action,
                                 Integer totalPrice,
                                 Integer balance) {
}
