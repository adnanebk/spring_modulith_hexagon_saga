package com.example.demo.notification.ports.out;

import com.example.demo.notification.domain.NotificationType;

public interface NotificationServicePort {

    void sendMessage(String to, String subject, String body);
    NotificationType getType();
}
