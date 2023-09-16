package com.example.ali.controller;

import com.example.ali.dto.ReviewRequestDto;
import com.example.ali.dto.ReviewResponseDto;
import com.example.ali.dto.StringResponseDto;
import com.example.ali.security.UserDetailsImpl;
import com.example.ali.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public ReviewResponseDto getReview(@PathVariable Long id) {
        return reviewService.getReview(id);
    }

    @PostMapping
    public ReviewResponseDto createReview(@RequestBody ReviewRequestDto requestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reviewService.createReview(requestDto, userDetails.getUser());
    }

    @PutMapping("/{id}")
    public ReviewResponseDto modifyBoard(@PathVariable Long id,
                                                        @RequestBody ReviewRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reviewService.updateReview(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/{id}")
    public StringResponseDto deleteBoard(@PathVariable Long id,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reviewService.deleteReview(id, userDetails.getUser());
    }
}
