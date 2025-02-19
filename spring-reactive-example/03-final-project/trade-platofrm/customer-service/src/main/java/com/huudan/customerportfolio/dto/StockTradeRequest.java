package com.huudan.customerportfolio.dto;

import com.huudan.customerportfolio.domain.Ticker;
import com.huudan.customerportfolio.domain.TradeAction;

public record StockTradeRequest(Ticker ticker,
                                Integer price,
                                Integer quantity,
                                TradeAction action) {

    public Integer totalPrice(){
        return price * quantity;
    }

}
