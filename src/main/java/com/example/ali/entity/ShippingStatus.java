package com.example.ali.entity;

import lombok.Getter;

@Getter
public enum ShippingStatus {
    DELIVERING("배송중"),
    DELIVERED("배송완료");

    // 배송중, 구매확정 ?  배송완료?

    ShippingStatus(String status) {
    }
}
