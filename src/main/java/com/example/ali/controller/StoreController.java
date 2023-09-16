package com.example.ali.controller;

import com.example.ali.dto.StoreRequestDto;
import com.example.ali.dto.StoreResponseDto;
import com.example.ali.security.UserDetailsImpl;
import com.example.ali.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/seller/store")
    public StoreResponseDto createStore(@RequestBody StoreRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return storeService.createStore(requestDto, userDetails.getUser());
    }

    @PutMapping("/seller/store")
    public StoreResponseDto updateStore(@RequestBody StoreRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return storeService.updateStore(requestDto);
    }

    @DeleteMapping("/seller/store")
    public StoreResponseDto deleteStore(@RequestBody StoreRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return storeService.deleteStore(requestDto);
    }


}
