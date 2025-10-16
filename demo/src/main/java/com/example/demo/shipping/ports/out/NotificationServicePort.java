package com.example.demo.shipping.ports.out;

import com.example.demo.shipping.domain.NotificationType;

public interface NotificationServicePort {

    void sendMessage(String to, String subject, String body);
    NotificationType getType();
}
