package com.example.ali.repository;

import com.example.ali.entity.Orders;
import com.example.ali.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

   List<Orders> findAllByUser_UserId(Long userId);

    Orders findByProduct_ProductIdAndUser_UserId(Long productId, Long storeId);
    List<Orders> findAllByProductIn(List<Product> sellerOwns);
}
