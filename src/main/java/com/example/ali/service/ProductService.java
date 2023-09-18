package com.example.ali.service;

import com.example.ali.dto.ProductRequestDto;
import com.example.ali.dto.ProductResponseDto;
import com.example.ali.entity.Product;
import com.example.ali.entity.ProductStatus;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

//    // 전체 조회  TODO : TEST용으로 임시 주석처리
//    @Transactional(readOnly = true)
//    public Page<ProductResponseDto> getProducts(int page, int size, String sortBy, boolean isAsc) {
//        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
//        Sort sort = Sort.by(direction, sortBy);
//        Pageable pageable = PageRequest.of(page, size, sort);
//        Page<Product> productList = productRepository.findAll(pageable);
//        return productList.map(ProductResponseDto::new);
//    }

    // 게시물 id로 조회
    public ProductResponseDto getProduct(Long productId) {
        Product product = findProduct(productId);
        return new ProductResponseDto(product);
    }

    // 상품 생성
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {

        Store store = findStore(requestDto.getStoreId());

        Product product = new Product(store, requestDto);
        Product saveProduct = productRepository.save(product);
        return new ProductResponseDto(saveProduct);
    }

    // 상품 정보 수정
    @Transactional
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto requestDto) {
        Product product = findProduct(productId);

        ProductStatus productStatus = null;
        if(requestDto.getProductStatus().equals("AVAILABLE")){
            productStatus = ProductStatus.AVAILABLE;
        }else
            productStatus = ProductStatus.DISCONTINUED;

        product.update(requestDto, productStatus);
        return new ProductResponseDto(product);
    }
    // 상품 정보 삭제
    public ProductResponseDto deleteProduct(Long productId) {
        Product product = findProduct(productId);
        productRepository.delete(product);
        return new ProductResponseDto(product);
    }

    // 해당 store 모든 정보가져오기
    public List<ProductResponseDto> getStoreProducts(Long storeId) {
        Store store = findStore(storeId);
        List<Product> productList = productRepository.findAllByStore(store);
        return productList.stream().map(ProductResponseDto::new).toList();
    }


    // store 정보 조회
    public Store findStore(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow(
            () -> new IllegalArgumentException("해당 상점을 찾을 수 없습니다.")
        );
    }

    // product 정보 조회
    public Product findProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
            () -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다.")
        );
    }

    // TODO : 테스트용
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll().stream().map(ProductResponseDto::new).toList();
    }
}