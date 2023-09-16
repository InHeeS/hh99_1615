package com.example.ali.entity;

import com.example.ali.dto.ProductRequestDto;
import com.example.ali.service.ProductService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productTitle;

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

    private String info;

    private Double price;

    private String productImage;

    private String productLink;

    // 현재 판매상태 (판매중, 품절, 판매중지)
    @Enumerated(value = EnumType.STRING)
    private ProductStatus productStatus;

    public Product(Store store, ProductRequestDto requestDto) {
        this.store = store;
        this.productTitle = requestDto.getProductTitle();
        this.info = requestDto.getInfo();
        this.price = requestDto.getPrice();
        this.productImage = requestDto.getProductImage();
        this.productLink = requestDto.getProductLink();
        this.productStatus = getProductStatus();
    }

    // getters and setters
    public void update(ProductRequestDto requestDto) {
        this.productTitle = requestDto.getProductTitle();
        this.info = requestDto.getInfo();
        this.price = requestDto.getPrice();
        this.productImage = requestDto.getProductImage();
        this.productLink = requestDto.getProductLink();
        this.productStatus = getProductStatus();
    }
}