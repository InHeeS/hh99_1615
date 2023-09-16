package com.example.ali.service;

import com.example.ali.dto.ReviewRequestDto;
import com.example.ali.dto.ReviewResponseDto;
import com.example.ali.dto.StringResponseDto;
import com.example.ali.entity.Orders;
import com.example.ali.entity.Product;
import com.example.ali.entity.Review;
import com.example.ali.entity.User;
import com.example.ali.repository.OrderRepository;
import com.example.ali.repository.ProductRepository;
import com.example.ali.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private ReviewRepository reviewRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    public ReviewResponseDto getReview(Long id) {
        Review review = findReviewById(id);
        return new ReviewResponseDto(review);
    }

    public ReviewResponseDto createReview(ReviewRequestDto requestDto, User user) {
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(()->
                new IllegalArgumentException("error")
        );
        Orders order = orderRepository.findById(requestDto.getOrderId()).orElseThrow(()->
                new IllegalArgumentException("error")
        );
        Review review = new Review(requestDto, user, product, order);

        return new ReviewResponseDto(review);
    }

    @Transactional
    public ReviewResponseDto updateReview(Long id, ReviewRequestDto requestDto, User user) {
        Review review = findReviewById(id);
        if (!review.getUser().equals(user)) {
            throw new IllegalArgumentException();
        }
        //Review의 comment, rating만 업데이트
        review.update(requestDto);

        return new ReviewResponseDto(review);
    }

    public StringResponseDto deleteReview(Long id, User user) {
        Review review = findReviewById(id);
        if (!review.getUser().equals(user)) {
            throw new IllegalArgumentException();
        }
        reviewRepository.delete(review);

        return new StringResponseDto("삭제성공");
    }

    private Review findReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("error")
        );
    }
}


