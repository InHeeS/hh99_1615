package com.example.ali.repository;

import com.example.ali.entity.Product;
import com.example.ali.entity.Store;
import com.example.ali.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByStore(Store store);
}
