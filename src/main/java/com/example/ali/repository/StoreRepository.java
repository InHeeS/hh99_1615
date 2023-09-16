package com.example.ali.repository;

import com.example.ali.entity.Store;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByUser_UserId(Long id);
}
