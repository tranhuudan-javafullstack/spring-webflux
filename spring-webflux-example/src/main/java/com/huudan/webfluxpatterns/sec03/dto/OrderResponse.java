package com.huudan.webfluxpatterns.sec03.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class OrderResponse {

    private Integer userId;
    private Integer productId;
    private UUID orderId;
    private Status status;
    private Address shippingAddress;
    private String expectedDelivery;

}
