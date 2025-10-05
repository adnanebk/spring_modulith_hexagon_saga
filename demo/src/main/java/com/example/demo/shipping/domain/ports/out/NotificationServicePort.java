package com.example.demo.shipping.domain.ports.out;

import com.example.demo.common.enums.NotificationType;

public interface NotificationServicePort {

    void sendMessage(String to, String subject, String body);
    NotificationType getType();
}
