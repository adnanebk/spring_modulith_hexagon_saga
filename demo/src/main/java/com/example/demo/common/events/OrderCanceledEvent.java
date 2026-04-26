package com.example.demo.common.events;

import com.example.demo.common.enums.OrderCancellingCause;
import com.example.demo.common.events.data.OrderData;

public record OrderCanceledEvent(OrderData data, OrderCancellingCause orderCancellingCause) {
}
