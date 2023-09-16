package com.example.ali.controller;

import com.example.ali.dto.ProductRequestDto;
import com.example.ali.dto.ProductResponseDto;
import com.example.ali.dto.StoreRequestDto;
import com.example.ali.dto.StoreResponseDto;
import com.example.ali.security.UserDetailsImpl;
import com.example.ali.service.ProductService;
import com.example.ali.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final ProductService productService;

    @PostMapping("/seller/store")
    public StoreResponseDto createStore(@RequestBody StoreRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return storeService.createStore(requestDto, userDetails.getUser());
    }

    @PutMapping("/seller/store")
    public StoreResponseDto updateStore(@RequestBody StoreRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return storeService.updateStore(requestDto,  userDetails.getUser());
    }

    @DeleteMapping("/seller/store")
    public StoreResponseDto deleteStore(@RequestBody StoreRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return storeService.deleteStore(requestDto,  userDetails.getUser());
    }

    // 스토어 상품 리스트 조회
    @GetMapping("/stores/products")

    // 상품 정보 등록
    @PostMapping("/stores/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto) {
        return productService.createProduct(requestDto);
    }

    // 상품 정보 수정
    @PutMapping("/stores/products/{productId}")
    public ProductResponseDto updateProduct(@RequestBody ProductRequestDto requestDto) {
        return productService.updateProduct(requestDto);
    }

    // 상품 정보 삭제
    @DeleteMapping("/stores/products/{productId}")
    public ProductResponseDto deleteProduct(@RequestBody ProductRequestDto requestDto) {
        return productService.deleteProduct(requestDto);
    }


}
