package com.example.ali.repository;

import com.example.ali.entity.Product;
import com.example.ali.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByStore(Store store);
}
