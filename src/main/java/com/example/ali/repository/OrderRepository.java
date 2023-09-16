package com.example.ali.repository;

import com.example.ali.dto.OrderResponseDto;
import com.example.ali.entity.Orders;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

   List<Orders> findAllByUser_UserId(Long userId);

    Orders findByProduct_ProductIdAndUser_UserId(Long productId, Long storeId);

}
