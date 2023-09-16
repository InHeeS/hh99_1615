package com.example.ali.entity;

import lombok.Getter;

@Getter
public enum ShippingStatus {
    a("배송중"),b("배송완료");

    // 배송중, 구매확정 ?  배송완료?

    ShippingStatus(String status) {
    }
}
