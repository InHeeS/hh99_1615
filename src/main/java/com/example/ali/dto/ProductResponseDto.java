package com.example.ali.dto;

import com.example.ali.entity.Product;
import com.example.ali.entity.Store;

public class ProductResponseDto {
    private Long id;
    private Store store;
    private String info;
    private Double price;

    public ProductResponseDto(Product product) {
        this.id = product.getProductId();
        this.store = product.getStore();
        this.info = product.getInfo();
        this.price = product.getPrice();
    }
}
