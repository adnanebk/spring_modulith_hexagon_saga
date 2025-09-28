package com.example.demo.common.events;

import com.example.demo.common.enums.OrderCancellingCause;
import com.example.demo.common.eventsdata.OrderEventData;

public class OrderCanceledEvent {
    private OrderEventData order;
    private OrderCancellingCause orderCancellingCause;
    public OrderCanceledEvent(OrderEventData order, OrderCancellingCause orderCancellingCause) {
        this.order = order;
        this.orderCancellingCause = orderCancellingCause;
    }
    public OrderEventData getOrder() {
        return order;
    }

    public OrderCancellingCause getOrderCancellingCause() {
        return orderCancellingCause;
    }
}
