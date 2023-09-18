package com.example.ali.service;

import com.example.ali.dto.ReviewRequestDto;
import com.example.ali.dto.ReviewResponseDto;
import com.example.ali.dto.StringResponseDto;
import com.example.ali.entity.*;
import com.example.ali.repository.OrderRepository;
import com.example.ali.repository.ProductRepository;
import com.example.ali.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public ReviewResponseDto getReview(Long id) {
        Review review = findReviewById(id);
        return new ReviewResponseDto(review);
    }

    public ReviewResponseDto createReview(ReviewRequestDto requestDto, User user) {
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(() ->
                new IllegalArgumentException("product없음")
        );
        Orders order = orderRepository.findById(requestDto.getOrderId()).orElseThrow(() ->
                new IllegalArgumentException("order없음")
        );
        if (!order.getShippingStatus().equals(ShippingStatus.DELIVERED)) {
            throw new IllegalArgumentException("배송완료된 제품만 리뷰 가능");
        }
        Optional<Review> review = reviewRepository
                .findByOrders_OrderId(requestDto.getOrderId());

        if (review.isPresent()) {
            throw new IllegalArgumentException("리뷰 중복됨");
        }

        Review newReview = new Review(requestDto, user, product, order);
        reviewRepository.save(newReview);

        return new ReviewResponseDto(newReview);
    }

    // 리뷰 업데이트
    @Transactional
    public ReviewResponseDto updateReview(Long id, ReviewRequestDto requestDto, User user) {
        Review review = findReviewById(id);
        if (!review.getUser().equals(user)) {
            throw new IllegalArgumentException("작성자만 접근가능");
        }
        //Review의 comment, rating만 업데이트
        review.update(requestDto);

        return new ReviewResponseDto(review);
    }

    // 리뷰 삭제
    public StringResponseDto deleteReview(Long id, User user) {
        Review review = findReviewById(id);
        if (!review.getUser().equals(user)) {
            throw new IllegalArgumentException("작성자만 접근가능");
        }
        reviewRepository.delete(review);

        return new StringResponseDto("삭제성공");
    }

    // 리뷰 정보 검색
    private Review findReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("error")
        );
    }
}


