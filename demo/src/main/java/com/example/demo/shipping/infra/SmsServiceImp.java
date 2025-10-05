package com.example.demo.shipping.infra;

import com.example.demo.common.enums.NotificationType;
import com.example.demo.shipping.domain.ports.out.NotificationServicePort;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImp implements NotificationServicePort {
    @Override
    public void sendMessage(String to, String subject, String body) {
        System.out.println("Message has been send successfully");
    }

    @Override
    public NotificationType getType() {
        return NotificationType.SMS;
    }
}
