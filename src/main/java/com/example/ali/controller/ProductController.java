package com.example.ali.controller;

import com.example.ali.dto.ProductResponseDto;
import com.example.ali.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    // 상품 리스트 전체 조회
//    @GetMapping("/products") // TODO : 테스트를 위해 주석처리 진행
//    public Page<ProductResponseDto> getProducts(
//            @RequestParam("page") int page,
//            @RequestParam("size") int size,
//            @RequestParam("sortBy") String sortBy,
//            @RequestParam("isAsc") boolean isAsc) {
//        return productService.getProducts(page - 1, size, sortBy, isAsc);
//    }

    @GetMapping("/products")
    public List<ProductResponseDto> getProducts() {
        return productService.getProducts();
    }

    // 상품 조회 (상세)
    @GetMapping("/products/{productId}")
    public ProductResponseDto getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

}
