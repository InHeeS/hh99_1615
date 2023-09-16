package com.example.ali.controller;


import com.example.ali.dto.OrderRequestDto;
import com.example.ali.dto.OrderResponseDto;
import com.example.ali.security.UserDetailsImpl;
import com.example.ali.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("checkstyle:Indentation")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/user/orders")
    public List<OrderResponseDto> getAllOrder() {
        return orderService.getAllOrder();
    }
    @PostMapping("/user/orders")
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.createOrder(requestDto, userDetails.getUser());
    }

    @GetMapping("/seller/orders")
    public List<OrderResponseDto> getAllSellerOrder( @AuthenticationPrincipal UserDetailsImpl userDetails){
        return orderService.getAllSellerOrder(userDetails.getUser());
    }

    @PutMapping("/seller/orders")
    public OrderResponseDto updateOrderShippingStatus(@RequestBody OrderRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails){
        return orderService.updateOrderShippingStatus(requestDto,userDetails.getUser());
    }

}
