package com.example.ali.repository;

import com.example.ali.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByOrders_OrderId(Long orderId);
}
