package com.example.demo.order.domain.mappers;

import com.example.demo.common.eventsdata.OrderEventData;
import com.example.demo.common.eventsdata.OrderItemEventData;
import com.example.demo.order.domain.models.OrderInput;
import com.example.demo.order.domain.models.OrderItem;

import java.util.List;


public class OrderEventMapper {

    public static OrderEventData toOrderEventData(OrderInput order, List<OrderItem> items){
        OrderEventData orderEventData = new OrderEventData();
        orderEventData.setId(order.getId());
        orderEventData.setShippingId(order.getOrderShipping().getId());
        orderEventData.setItems(items.stream().map(OrderEventMapper::toOrderItemEventData).toList());
        orderEventData.setPaymentId(order.getPaymentInfo().getId());
        orderEventData.setPaymentType(order.getPaymentInfo().getType());
        return  orderEventData;
    }
    public static OrderItemEventData toOrderItemEventData(OrderItem orderItem){
        OrderItemEventData orderItemEventData = new OrderItemEventData();
        orderItemEventData.setId(orderItem.getId());
        orderItemEventData.setProductId(orderItem.getProductId());
        orderItemEventData.setQuantity(orderItem.getQuantity());
        orderItemEventData.setPrice(orderItem.getPrice());
        return orderItemEventData;
    }
}
