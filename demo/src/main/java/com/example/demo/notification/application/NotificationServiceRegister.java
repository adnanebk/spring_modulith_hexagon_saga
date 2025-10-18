package com.example.demo.notification.application;

import com.example.demo.notification.domain.NotificationType;
import com.example.demo.notification.ports.out.NotificationServicePort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NotificationServiceRegister {

    private final Map<NotificationType, NotificationServicePort> services;
    private final Map<UUID, NotificationType> notificationTypeMap = new ConcurrentHashMap<>();

    public NotificationServiceRegister(List<NotificationServicePort> notificationServices) {
        this.services = notificationServices.stream()
                .collect(Collectors.toMap(NotificationServicePort::getType, Function.identity()));
    }

    public NotificationServicePort getService(NotificationType type) {
      return services.get(type);
    }

    public void switchNotificationType(UUID id) {
        int ordinal = getNotificationType(id).ordinal();
        var notificationTypes = NotificationType.values();
        NotificationType nextType = NotificationType.values()[(ordinal + 1) % notificationTypes.length];
        notificationTypeMap.put(id,nextType);
    }

    public NotificationType getNotificationType(UUID id) {
        return notificationTypeMap.getOrDefault(id, NotificationType.EMAIL);
    }
}
