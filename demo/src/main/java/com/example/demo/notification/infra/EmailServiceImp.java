package com.example.demo.notification.infra;

import com.example.demo.notification.domain.NotificationType;
import com.example.demo.notification.ports.out.NotificationServicePort;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements NotificationServicePort {
    @Override
    public void sendMessage(String to, String subject, String body) {
        System.out.println("Email has been send successfully");
    }

    @Override
    public NotificationType getType() {
        return NotificationType.EMAIL;
    }
}
