package com.example.ali.dto;

import com.example.ali.entity.Product;
import com.example.ali.entity.ProductStatus;
import com.example.ali.entity.Store;
import lombok.Getter;

@Getter
public class ProductResponseDto {
    private Long productId;
    private String productTitle;
    private Long storeId;
    private String storeName;
    private String sellerName;
    private String info;
    private Double price;
    private Long quantity;
    private String productImage;
    private String productLink;
    private ProductStatus productStatus;

    public ProductResponseDto(Product product) {
        this.productId = product.getProductId();
        this.productTitle = product.getProductTitle();
        this.storeName = product.getStore().getStoreName();
        this.storeId = product.getStore().getStoreId();
        this.sellerName = product.getStore().getSellerName();
        this.info = product.getInfo();
        this.price = product.getPrice();
        this.quantity = product.getStock();
        this.productImage = product.getProductImage();
        this.productLink = product.getProductLink();
        this.productStatus = product.getProductStatus();
    }
}
