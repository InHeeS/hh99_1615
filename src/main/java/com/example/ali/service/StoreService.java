package com.example.ali.service;

import com.example.ali.dto.StoreRequestDto;
import com.example.ali.dto.StoreResponseDto;
import com.example.ali.entity.Store;
import com.example.ali.entity.User;
import com.example.ali.repository.StoreRepository;
import com.example.ali.repository.UserRepository;
import com.example.ali.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreResponseDto createStore(StoreRequestDto requestDto, User user) {

        //RequestDto -> Entity
        Store store = new Store(requestDto,user);
        //DB 저장
        Store savedStore = storeRepository.save(store);
        //Entity -> ResponseDto
        return new StoreResponseDto(savedStore);
    }

    @Transactional
    public StoreResponseDto updateStore(StoreRequestDto requestDto, User user) {

        // Store 안의 해당 User의 user_id 값으로 store 가져오기
        Long id = user.getUserId();
        Store store = storeRepository.findByUser_UserId(id).orElseThrow(() ->
            new IllegalArgumentException("등록된 Store 가 없습니다."));

        store.update(requestDto);

        return new StoreResponseDto(store);
    }

    public StoreResponseDto deleteStore(User user) {

        Long id = user.getUserId();
        Store store = storeRepository.findByUser_UserId(id).orElseThrow(() ->
            new IllegalArgumentException("등록된 Store 가 없습니다."));

        storeRepository.delete(store);

        return new StoreResponseDto(store);
    }
}
