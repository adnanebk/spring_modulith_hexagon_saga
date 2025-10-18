package com.example.demo.notification.application;

import com.example.demo.common.events.NotificationEvent;
import com.example.demo.notification.domain.NotificationType;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;



@Component
public class NotificationListener {

    private NotificationServiceRegister notificationServiceRegister;

    public NotificationListener(NotificationServiceRegister notificationServiceRegister) {
        this.notificationServiceRegister = notificationServiceRegister;
    }

    @ApplicationModuleListener
    public void handleNotificationEvent(NotificationEvent event) {
        try{
            NotificationType type = notificationServiceRegister.getNotificationType(event.getId());
            notificationServiceRegister.getService(type)
                    .sendMessage(event.getEmail() , event.getSubject(), event.getBody());
        } catch (RuntimeException ex){
            notificationServiceRegister.switchNotificationType(event.getId());
            throw ex;
        }
    }
}
