package com.example.demo.order.application;


import com.example.demo.common.data.OrderDetails;
import com.example.demo.common.data.OrderedItem;
import com.example.demo.common.data.StockedProduct;
import com.example.demo.common.events.OrderCanceledEvent;
import com.example.demo.common.events.OrderPlacedEvent;
import com.example.demo.common.data.CouponCodeUsage;
import com.example.demo.order.domain.Order;
import com.example.demo.order.domain.OrderStatus;
import com.example.demo.order.ports.in.OrderRequest;
import com.example.demo.order.ports.in.OrderServicePort;
import com.example.demo.order.ports.out.DiscountRepoClient;
import com.example.demo.order.ports.out.OrderRepoPort;
import com.example.demo.order.ports.out.ProductClientPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class OrderService implements OrderServicePort {

    private final OrderRepoPort orderRepoPort;
    private final ProductClientPort productClientPort;
    private final ApplicationEventPublisher publisher;
    private final DiscountRepoClient discountRepoClient;

    public OrderService(OrderRepoPort orderRepoPort, ProductClientPort productClientPort, ApplicationEventPublisher publisher, DiscountRepoClient discountRepoClient) {
        this.orderRepoPort = orderRepoPort;
        this.productClientPort = productClientPort;
        this.publisher = publisher;
        this.discountRepoClient = discountRepoClient;
    }

    @Transactional
    @Override
    public Integer placeOrder(OrderRequest orderRequest) {
        List<OrderedItem> orderItems = orderRequest.orderItems();
        if (CollectionUtils.isEmpty(orderItems))
            throw new IllegalArgumentException("Order must contain at least one item");

        Map<Integer, Integer> productQuantityMap = orderItems.stream()
                .collect(Collectors.toMap(OrderedItem::productId, OrderedItem::quantity,Integer::sum));

        List<StockedProduct> productsInStock = productClientPort.getProducts(new ArrayList<>(productQuantityMap.keySet()));

        orderItems = addPriceToItems(orderItems, productsInStock);

        Order order = Order.create(orderRequest.userId(), orderItems);

        if(StringUtils.hasText(orderRequest.couponCode())){
              BigDecimal amountAfterDiscount =  discountRepoClient.discount(order.getUserId(), orderRequest.couponCode(), order.getTotalPrice());
              order.applyDiscount(amountAfterDiscount,orderRequest.couponCode());
        }

        Integer orderId = orderRepoPort.create(order);

        OrderDetails orderDetails = new OrderDetails(orderId, orderRequest.userId(), orderRequest.paymentToken(), orderItems);
        publisher.publishEvent(new OrderPlacedEvent(orderDetails));
        return orderId;
    }


    @Transactional
    @Override
    public void cancelOrder(Integer orderId, String message) {
        orderRepoPort.updateStatus(orderId, OrderStatus.CANCELLED);
        publisher.publishEvent(new OrderCanceledEvent(orderId,message));
    }

    @Transactional
    @Override
    public void completeOrder(Integer orderId, Integer userId) {
        orderRepoPort.updateStatus(orderId, OrderStatus.COMPLETED);
        orderRepoPort.findCouponCodeById(orderId).ifPresent(coupon->{
            discountRepoClient.saveCouponUsage(new CouponCodeUsage(userId, orderId, coupon));
        });
    }

    private  List<OrderedItem> addPriceToItems(List<OrderedItem> orderItems, List<StockedProduct> productsInStock) {
        return orderItems.stream().map(item -> {
            StockedProduct product = searchProductInStock(productsInStock, item);
            return item.withPrice(product.price());
        }).toList();
    }


    private StockedProduct searchProductInStock(List<StockedProduct> productsInStock, OrderedItem item) {
        return productsInStock.stream().filter(p -> p.productId().equals(item.productId())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product with id %s is not in stock".formatted(item.productId())));
    }


}
