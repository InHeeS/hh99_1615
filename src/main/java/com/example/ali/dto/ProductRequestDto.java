package com.example.ali.dto;

import lombok.Getter;

@Getter
public class ProductRequestDto {
    private Long storeId;
    private String productTitle;
    private String info;
    private Double price;
    private String productImage;
    private String productLink;
    private String productStatus;
}
