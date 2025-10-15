package com.example.demo.order.application;

import com.example.demo.common.events.data.OrderData;
import com.example.demo.common.events.data.OrderItemData;
import com.example.demo.common.events.data.OrderPaymentData;
import com.example.demo.common.events.data.OrderShippingData;
import com.example.demo.order.domain.models.Order;
import com.example.demo.order.domain.models.OrderPayment;
import com.example.demo.order.domain.models.OrderShipping;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class OrderDataMapper {

    public static OrderData mapToOrderData(Integer id, Order order) {
        return new OrderData(id, mapToOrderItemData(order),
                mapToOrderShippingData(order.getShipping()),
                mapToOrderPaymentData(order.getPaymentInfo()),new ConcurrentHashMap<>());
    }

    public static OrderPaymentData mapToOrderPaymentData( OrderPayment paymentInfo) {
      return new OrderPaymentData(paymentInfo.getType());
    }

    public static OrderShippingData mapToOrderShippingData(OrderShipping shipping) {
        return new OrderShippingData(shipping.getName(),shipping.getEmail(),shipping.getAddress(),shipping.getPhone());
    }

    public static List<OrderItemData> mapToOrderItemData(Order order) {
        return order.getItems().stream().map(item -> new OrderItemData(item.getPrice(), item.getQuantity(), item.getProductId())).toList();
    }
}
