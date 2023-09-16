package com.example.ali.service;

import com.example.ali.controller.WebSocketController;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    private final WebSocketController webSocketController;

    public DeliveryService(WebSocketController webSocketController) {
        this.webSocketController = webSocketController;
    }

    public void completeDelivery(String message) {
        // 배달 완료 로직...

        webSocketController.sendDeliveryCompleteNotification(message);
    }
}
