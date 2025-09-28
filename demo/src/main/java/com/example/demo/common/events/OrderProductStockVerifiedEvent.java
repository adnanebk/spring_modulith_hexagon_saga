package com.example.demo.common.events;

import com.example.demo.common.eventsdata.OrderEventData;

public class OrderProductStockVerifiedEvent {
    private OrderEventData order;
    public OrderProductStockVerifiedEvent(OrderEventData order) {
        this.order = order;
    }
    public OrderEventData getOrder() {
        return order;
    }
}
