package com.example.ali.entity;

import com.example.ali.dto.ReviewRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Orders orders;

    private String comment;
    private Integer rating;

    public Review(ReviewRequestDto requestDto, User user, Product product, Orders order) {
        this.user = user;
        this.product = product;
        this.orders = order;
        this.comment = requestDto.getComment();
        this.rating = requestDto.getRating();
    }

    public void update(ReviewRequestDto requestDto) {
        this.comment = requestDto.getComment();
        this.rating = requestDto.getRating();
    }

    // getters and setters
}