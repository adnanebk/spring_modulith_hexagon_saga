package com.example.demo.common.events;

import com.example.demo.common.enums.OrderCancellingCause;
import com.example.demo.common.events.data.OrderData;

public class OrderCanceledEvent {
    private OrderData data;
    private OrderCancellingCause orderCancellingCause;
    public OrderCanceledEvent(OrderData data, OrderCancellingCause orderCancellingCause) {
        this.data = data;
        this.orderCancellingCause = orderCancellingCause;
    }
    public OrderData getData() {
        return data;
    }

    public OrderCancellingCause getOrderCancellingCause() {
        return orderCancellingCause;
    }
}
