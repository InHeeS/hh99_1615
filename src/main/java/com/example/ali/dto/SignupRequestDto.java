package com.example.ali.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @Size(min = 4, max = 10, message = "아이디 : 4글자~10글자 사이만 가능합니다.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "아이디 : 소문자, 숫자만 가능합니다.")
    String username;

    @Size(min = 8, max = 15, message = "비밀번호 : 8글자~15글자 사이만 가능합니다.")
    @Pattern(regexp = "^[!-~]+$", message = "비밀번호 : 대소문자, 특수문자만 가능합니다.")
    String password;

    String email;
    private boolean seller = false;
}
