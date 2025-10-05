package com.example.demo.shipping.application;

import com.example.demo.common.enums.NotificationType;
import com.example.demo.shipping.domain.ports.out.NotificationServicePort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class NotificationService {

    private final Map<NotificationType, NotificationServicePort> services;

    private final Map<Integer,NotificationType> notificationType = new ConcurrentHashMap<>();

    public NotificationService(List<NotificationServicePort> notificationServices) {
        this.services = notificationServices.stream()
                .collect(Collectors.toMap(
                        NotificationServicePort::getType,
                        service -> service
                ));
    }

    public void sendMessage(Integer id, String to, String subject, String body) {
         try{
             services.get(getNotificationType(id))
                     .sendMessage(to, subject, body);
         } catch (RuntimeException ex){
             notificationType.put(id,switchNotificationType(id));
             throw ex;
         }
    }

    private NotificationType switchNotificationType(Integer id) {
        return NotificationType.values()[getNotificationType(id).ordinal() + 1 % NotificationType.values().length];
    }

    private NotificationType getNotificationType(Integer id) {
        return notificationType.getOrDefault(id, NotificationType.EMAIL);
    }
}
