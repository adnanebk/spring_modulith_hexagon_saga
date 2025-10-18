package com.example.demo.common.events;

import java.util.UUID;

public class NotificationEvent {

    private final UUID id;
    private final String email;
    private final String subject;
    private final String body;

    public NotificationEvent(UUID id, String email, String subject, String body) {
        this.id = id;
        this.email = email;
        this.subject = subject;
        this.body = body;
    }

    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public UUID getId() {
        return id;
    }
}
