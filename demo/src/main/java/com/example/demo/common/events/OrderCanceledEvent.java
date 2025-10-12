package com.example.demo.common.events;

import com.example.demo.common.enums.OrderCancellingCause;
import com.example.demo.common.models.Order;

public class OrderCanceledEvent {
    private Order order;
    private OrderCancellingCause orderCancellingCause;
    public OrderCanceledEvent(Order order, OrderCancellingCause orderCancellingCause) {
        this.order = order;
        this.orderCancellingCause = orderCancellingCause;
    }
    public Order getOrder() {
        return order;
    }

    public OrderCancellingCause getOrderCancellingCause() {
        return orderCancellingCause;
    }
}
