package com.example.ali.service;

import com.example.ali.dto.ProductRequestDto;
import com.example.ali.dto.ProductResponseDto;
import com.example.ali.entity.Product;
import com.example.ali.entity.Store;
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
        Product product = findProduct(productId);
        return new ProductResponseDto(product);
    }

    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Store store = findStore(requestDto.getStoreId());
        Product product = new Product(store, requestDto);
        productRepository.save(product);
        return new ProductResponseDto(product);
    }

    // 상품 정보 수정
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto requestDto) {
        Product product = findProduct(productId);
        product.update(requestDto);
        return new ProductResponseDto(product);
    }

    public ProductResponseDto deleteProduct(Long productId, ProductRequestDto requestDto) {
        Product product = findProduct(productId);
        productRepository.delete(product);
        return new ProductResponseDto(product);
    }


    public Store findStore(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(
            () -> new IllegalArgumentException("해당 상점을 찾을 수 없습니다.")
        );
    }



    public Product findProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
            () -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다.")
        );
    }


    public Page<ProductResponseDto> getStoreProducts(Long storeId) {
        Store store = findStore(storeId);
        Page<Product> productList = productRepository.findAllByStore(store);
        return productList.map(ProductResponseDto::new);
    }
}