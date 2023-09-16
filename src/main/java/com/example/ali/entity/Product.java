package com.example.ali.entity;

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

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

    private String info;
    private Double price;

    // 현재 판매상태 (판매중, 품절, 판매중지)
    @Enumerated(value = EnumType.STRING)
    private ProductStatus productStatus;

    // getters and setters
}