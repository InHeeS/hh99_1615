package com.example.ali.dto;

import com.example.ali.entity.Product;
import com.example.ali.entity.ProductStatus;
import com.example.ali.entity.Store;
import lombok.Getter;

@Getter
public class ProductResponseDto {
    private Long productId;
    private String productTitle;
    private Store store;
    private String info;
    private Double price;
    private String productImage;
    private String productLink;
    private ProductStatus productStatus;

    public ProductResponseDto(Product product) {
        this.productId = product.getProductId();
        this.productTitle = product.getProductTitle();
        this.store = product.getStore();
        this.info = product.getInfo();
        this.price = product.getPrice();
        this.productImage = product.getProductImage();
        this.productLink = product.getProductLink();
        this.productStatus = product.getProductStatus();
    }
}
