package com.example.ali.dto;

import lombok.Getter;

@Getter
public class StringResponseDto {
    String message;

    public StringResponseDto(String message) {
        this.message = message;
    }
}
