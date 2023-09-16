package com.example.ali.entity;

import com.example.ali.dto.StoreRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    private String sellerName;
    private Double income;
    private String info;
    private String storeName;

    public Store(StoreRequestDto requestDto, User user) {
        this.sellerName = requestDto.getSellerName();
        this.income = requestDto.getIncome();
        this.info = requestDto.getInfo();
        this.storeName = user.getUsername(); // storeName
        this.user = user;
    }

    public void update(StoreRequestDto requestDto) {
        this.sellerName = requestDto.getSellerName();
        this.income = requestDto.getIncome();
        this.info = requestDto.getInfo();
        this.storeName = requestDto.getSellerName();
    }
}