package com.example.ali.dto;

import lombok.Getter;

@Getter
public class ReviewRequestDto {
    private Long productId;
    private Long orderId;
    private String comment;
    private Integer rating;
}
