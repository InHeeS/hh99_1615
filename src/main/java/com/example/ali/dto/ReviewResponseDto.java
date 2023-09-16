package com.example.ali.dto;

import com.example.ali.entity.Review;
import lombok.Getter;

@Getter
public class ReviewResponseDto {
    private String username;
    private Long productId;
    private Long orderId;
    private String comment;
    private Integer rating;

    public ReviewResponseDto(Review review) {
        this.username = review.getUser().getUsername();
        this.productId = review.getProduct().getProductId();
        this.orderId = review.getOrders().getOrderId();
        this.comment = review.getComment();
        this.rating = review.getRating();
    }
}
