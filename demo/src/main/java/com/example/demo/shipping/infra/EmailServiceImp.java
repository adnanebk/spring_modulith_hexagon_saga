package com.example.demo.shipping.infra;

import com.example.demo.shipping.domain.NotificationType;
import com.example.demo.shipping.ports.out.NotificationServicePort;
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
