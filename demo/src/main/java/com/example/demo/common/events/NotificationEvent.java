package com.example.demo.common.events;

import java.util.UUID;

public record NotificationEvent(UUID id, String email, String subject, String body) {

}
