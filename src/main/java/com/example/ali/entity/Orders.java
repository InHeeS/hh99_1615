package com.example.ali.entity;

import com.example.ali.dto.OrderRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;


    private LocalDateTime orderDate;
//    private java.sql.Date orderDate;

    @Enumerated(value = EnumType.STRING)
    private ShippingStatus shippingStatus;

    public Orders(User user, Product product) {
        this.user = user;
        this.product = product;
        this.shippingStatus = ShippingStatus.a;
    }


    // getters and setters
}