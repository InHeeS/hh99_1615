package com.example.ali.dto;

import lombok.Getter;

@Getter
public class ReviewRequestDto {
    private Long orderId;
    private Long productId;
    private String comment;
    private Integer rating;
}
