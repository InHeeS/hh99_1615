package com.example.ali.repository;

import com.example.ali.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByUser_UserId(Long id);
}
