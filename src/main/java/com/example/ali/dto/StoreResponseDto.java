package com.example.ali.dto;

import com.example.ali.entity.Store;
import lombok.Getter;

@Getter
public class StoreResponseDto {

    private Long storeId;
    private String sellerName;
    private Double income;
    private String info;
    private String storeName;

    public StoreResponseDto(Store savedStore) {
        this.storeId = savedStore.getStoreId();
        this.sellerName = savedStore.getSellerName();
        this.income = savedStore.getIncome();
        this.info = savedStore.getInfo();
        this.storeName = savedStore.getStoreName();
    }
}
