package com.example.ali.dto;

import com.example.ali.entity.Orders;
import lombok.Getter;

@Getter
public class OrderResponseDto {
    private Long orderId;
    private String username;
    private Long productId; //productname??

    public OrderResponseDto(Orders order) {
        this.orderId = order.getOrderId();
        this.username = order.getUser().getUsername();
        this.productId = order.getProduct().getProductId();
    }
}