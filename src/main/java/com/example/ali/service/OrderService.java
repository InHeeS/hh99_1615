package com.example.ali.service;

import com.example.ali.dto.OrderRequestDto;
import com.example.ali.dto.OrderResponseDto;
import com.example.ali.entity.Orders;
import com.example.ali.entity.Product;
import com.example.ali.entity.ShippingStatus;
import com.example.ali.entity.User;
import com.example.ali.repository.OrderRepository;
import com.example.ali.repository.ProductRepository;
import com.example.ali.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("checkstyle:Indentation")
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    public List<OrderResponseDto> getAllOrder() {
        return orderRepository.findAll().stream().map(OrderResponseDto::new).toList();
    }
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto, User user) {

        //requestDto에서 요청한 productId로 product찾기
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(() ->
                new IllegalArgumentException()
        );
        //user의 돈이 product가격보다 큰지 확인
        if (user.getPoint() < product.getPrice()) {
            throw new IllegalArgumentException();
        }
        //user의 포인트 차감
        user.changePoint(product.getPrice());

        //Order 생성, DB 저장
        Orders order = new Orders(user, product);
        orderRepository.save(order);

        return new OrderResponseDto(order);
    }

    public List<OrderResponseDto> getAllSellerOrder(User user) {
        // 해당 스토어 아이디인 user_id
        return orderRepository.findAllByUserId(user.getUserId()).stream().map(OrderResponseDto::new).toList();
    }

    @Transactional
    public OrderResponseDto updateOrderShippingStatus(OrderRequestDto requestDto, User user) {

        Long storeId = user.getUserId();
        Orders order = orderRepository.findByProductIdAndUserId(requestDto.getProductId(), storeId);

        ShippingStatus shippingStatus = order.getShippingStatus();
        if(shippingStatus.equals(ShippingStatus.a))
            shippingStatus = ShippingStatus.b;
        else
            shippingStatus = ShippingStatus.a;

        return new OrderResponseDto(order);
    }
}
