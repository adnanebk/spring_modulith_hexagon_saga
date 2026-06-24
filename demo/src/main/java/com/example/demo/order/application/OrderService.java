package com.example.demo.order.application;


import com.example.demo.common.data.OrderDetails;
import com.example.demo.common.data.OrderedItem;
import com.example.demo.common.events.OrderCanceledEvent;
import com.example.demo.common.events.OrderCompletedEvent;
import com.example.demo.common.events.OrderPlacedEvent;
import com.example.demo.order.domain.Order;
import com.example.demo.order.domain.OrderStatus;
import com.example.demo.order.ports.in.OrderRequest;
import com.example.demo.order.ports.in.OrderServicePort;
import com.example.demo.order.ports.out.DiscountClientPort;
import com.example.demo.order.ports.out.OrderRepoPort;
import com.example.demo.order.ports.out.ProductClientPort;
import com.example.demo.stock.domain.ProductInStock;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class OrderService implements OrderServicePort {

    private final OrderRepoPort orderRepoPort;
    private final ProductClientPort productClientPort;
    private final ApplicationEventPublisher publisher;
    private final DiscountClientPort discountClientPort;

    public OrderService(OrderRepoPort orderRepoPort, ProductClientPort productClientPort, ApplicationEventPublisher publisher, DiscountClientPort discountClientPort) {
        this.orderRepoPort = orderRepoPort;
        this.productClientPort = productClientPort;
        this.publisher = publisher;
        this.discountClientPort = discountClientPort;
    }

    @Transactional
    @Override
    public Order placeOrder(OrderRequest orderRequest) {
        List<OrderedItem> orderItems = orderRequest.orderItems();
        OrderValidator.validateItems(orderItems);

        List<ProductInStock> productsInStock = getProductsInStock(orderItems);

        Map<Integer, ProductInStock> productsInStockMap = productsInStock.stream()
                .collect(Collectors.toMap(ProductInStock::productId, Function.identity()));

        OrderValidator.validateItemsAvailability(productsInStockMap, orderItems);

        Order order = Order.create(orderRequest.userId(), addPriceToItems(orderItems, productsInStockMap));

        applyDiscountIfExist(orderRequest.couponCode(), order);

        Integer orderId = orderRepoPort.create(order);
        order.setId(orderId);
        OrderDetails orderDetails = new OrderDetails(orderId, orderRequest.userId(), orderRequest.paymentToken(), order.getItems(),
                order.getCouponCode(), order.getTotalPrice());
        publisher.publishEvent(new OrderPlacedEvent(orderDetails));
        return order;
    }


    @Transactional
    @Override
    public void cancelOrder(Integer orderId, String message) {
        orderRepoPort.updateStatus(orderId, OrderStatus.CANCELLED);
        publisher.publishEvent(new OrderCanceledEvent(orderId,message));
    }

    @Transactional
    @Override
    public void completeOrder(OrderDetails orderDetails) {
        orderRepoPort.updateStatus(orderDetails.orderId(), OrderStatus.COMPLETED);
        this.publisher.publishEvent(new OrderCompletedEvent(orderDetails.orderId(), orderDetails.userId(), orderDetails.couponCode()));
    }
    @Transactional(readOnly = true)
    @Override
    public List<Order> getOrderByUserId(Integer userId) {
        return orderRepoPort.findByUserId(userId);
    }

    private void applyDiscountIfExist(String couponCode, Order order) {
        if(StringUtils.hasText(couponCode)){
            BigDecimal amountAfterDiscount =  discountClientPort.discount(order.getUserId(), couponCode, order.getTotalPrice());
            order.applyDiscount(amountAfterDiscount, couponCode);
        }
    }
    private static List<OrderedItem> addPriceToItems(List<OrderedItem> items,Map<Integer, ProductInStock> productsMap) {
        return items.stream().map(item ->
                item.withPrice(productsMap.get(item.productId()).price())).toList();
    }
    private List<ProductInStock> getProductsInStock(List<OrderedItem> orderItems) {
         List<Integer> productIds= orderItems.stream().map(OrderedItem::productId).toList();
        return productClientPort.getProductsByIds(productIds);
    }

}
