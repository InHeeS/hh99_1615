package com.example.ali.service;

import com.example.ali.dto.SignupRequestDto;
import com.example.ali.dto.StringResponseDto;
import com.example.ali.entity.User;
import com.example.ali.entity.UserRoleEnum;
import com.example.ali.jwt.JwtUtil;
import com.example.ali.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public StringResponseDto signup(SignupRequestDto requestDto) {

        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();

        Optional<User> tmpUser = userRepository.findByUsername(username);
        if (tmpUser.isPresent()) {
            throw new IllegalArgumentException("중복 id 오류");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isSeller()) {
            role = UserRoleEnum.SELLER;
        }
        User user = new User(username, password, email, role);
        userRepository.save(user);
        return new StringResponseDto("새로운 아이디 저장 성공");

    }
}
