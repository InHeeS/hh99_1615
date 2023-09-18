package com.example.ali.service;

import com.example.ali.dto.OrderRequestDto;
import com.example.ali.dto.OrderResponseDto;
import com.example.ali.entity.*;
import com.example.ali.repository.OrderRepository;
import com.example.ali.repository.ProductRepository;
import java.util.List;

import com.example.ali.repository.StoreRepository;
import com.example.ali.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("checkstyle:Indentation")
@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    public List<OrderResponseDto> getAllOrder(User user) {

        if (user.getRole().equals(UserRoleEnum.USER)) {
            return orderRepository.findAllByUser_UserId(user.getUserId()).stream()
                .map(OrderResponseDto::new)
                .toList();
        }
            Store store = storeRepository.findByUser_UserId(user.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("잘못된 정보")); // 여기서 한번

            List<Product> products = productRepository.findAllByStore(store); // 여기서 한번

            return orderRepository.findAllByProductIn(products).stream()// 여기서 한번
                    .map(OrderResponseDto::new)
                    .toList();
    }

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto, User user) {

        //user가 seller가 아닌 것을 확인
        if (!user.getRole().equals(UserRoleEnum.USER)) {
            throw new IllegalArgumentException("seller는 물품 구매 불가");
        }
        //requestDto에서 요청한 productId로 product찾기
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(() ->
                new IllegalArgumentException()
        );
        //user의 돈이 product가격보다 큰지 확인
        if (user.getPoint() < product.getPrice() * requestDto.getQuantity()) {
            throw new IllegalArgumentException("돈부족");
        }
        //user의 포인트 차감
        User usertmp = userRepository.findById(user.getUserId()).orElseThrow(() ->
                new IllegalArgumentException("일치하는유저없음"));
        usertmp.changePoint(product.getPrice() * requestDto.getQuantity());

        //Order 생성, DB 저장
        Orders order = new Orders(user, product);
        orderRepository.save(order);

        return new OrderResponseDto(order);
    }

    @Transactional
    public OrderResponseDto updateOrderShippingStatus(Long orderId, User user) {
        // Role 권한 확인
        if (!user.getRole().equals(UserRoleEnum.SELLER)) {
            throw new IllegalArgumentException("seller만 배송상태 수정 가능");
        }
        // 주문 있없?
        Orders order = orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("해당 주문이 없습니다.")
        );
        // 니가 이 물건의 주인이냐
        if (!order.getProduct().getStore().getUser().equals(user)) {
            // 쿼리 튜닝 필요
            throw new IllegalArgumentException("수정 권한이 없습니다");
        }
        if (order.getShippingStatus().equals(ShippingStatus.DELIVERING)) {
            order.changeStatus();
        }
        return new OrderResponseDto(order);
    }
}
