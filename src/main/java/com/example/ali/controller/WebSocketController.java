package com.example.ali.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate template;

    public WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/deliveryStatus")
    @SendTo("/topic/notification")
    public String sendDeliveryStatus(String message) {
        return message;
    }

    public void sendDeliveryCompleteNotification(String message) {
        this.template.convertAndSend("/topic/notification", "배달 완료: " + message);
    }
}
