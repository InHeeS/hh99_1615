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
@RequestMapping("/api/user/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/category/{category}")
    public List<OrderResponseDto> getBoard() {
        return orderService.getAllOrder();
    }
    @PostMapping("")
    public OrderResponseDto createBoard(@RequestBody OrderRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.createOrder(requestDto, userDetails.getUser());
    }

}
