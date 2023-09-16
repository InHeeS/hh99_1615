package com.example.ali.service;

import com.example.ali.dto.ProductRequestDto;
import com.example.ali.dto.ProductResponseDto;
import com.example.ali.entity.Product;
import com.example.ali.entity.Store;
import com.example.ali.entity.User;
import com.example.ali.entity.UserRoleEnum;
import com.example.ali.repository.ProductRepository;
import com.example.ali.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    // 전체 조회
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getProducts(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productList = productRepository.findAll(pageable);
        return productList.map(ProductResponseDto::new);
    }

    // 게시물 id로 조회
    public ProductResponseDto getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다.")
        );
        return new ProductResponseDto(product);
    }

    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Store store = storeRepository.findById(requestDto.getStoreId()).orElseThrow(
                () -> new IllegalArgumentException("해당 상점을 찾을 수 없습니다.")
        );
        Product product = new Product(requestDto);
        productRepository.save(product);
        return new ProductResponseDto(product);
    }

}
