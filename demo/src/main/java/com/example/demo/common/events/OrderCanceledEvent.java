package com.example.demo.common.events;

import com.example.demo.common.enums.OrderCancellingCause;
import com.example.demo.common.models.OrderInput;

public class OrderCanceledEvent {
    private OrderInput order;
    private OrderCancellingCause orderCancellingCause;
    public OrderCanceledEvent(OrderInput order, OrderCancellingCause orderCancellingCause) {
        this.order = order;
        this.orderCancellingCause = orderCancellingCause;
    }
    public OrderInput getOrder() {
        return order;
    }

    public OrderCancellingCause getOrderCancellingCause() {
        return orderCancellingCause;
    }
}
