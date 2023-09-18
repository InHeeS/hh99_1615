package com.example.ali.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {
    // 판매중
    AVAILABLE,
    // 일시품절
    OUT_OF_STOCK,
    // 품절
    DISCONTINUED;

}
