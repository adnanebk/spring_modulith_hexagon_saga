package com.example.demo.order.application;


import com.example.demo.common.data.OrderDetails;
import com.example.demo.common.data.OrderedItem;
import com.example.demo.common.data.StockedProduct;
import com.example.demo.common.events.OrderCanceledEvent;
import com.example.demo.common.events.OrderPlacedEvent;
import com.example.demo.order.domain.Order;
import com.example.demo.order.domain.OrderItem;
import com.example.demo.order.domain.OrderStatus;
import com.example.demo.order.ports.in.OrderServicePort;
import com.example.demo.order.ports.out.DiscountRepoClient;
import com.example.demo.order.ports.out.OrderRepoPort;
import com.example.demo.order.ports.out.ProductClientPort;
import jakarta.annotation.Nullable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public Integer placeOrder(Integer userId, List<OrderItem> orderItems, String paymentToken,@Nullable String couponCode) {
        if (orderItems == null || orderItems.isEmpty())
            throw new IllegalArgumentException("Order must contain at least one item");

        Map<Integer, Integer> quantityMap = orderItems.stream()
                .collect(Collectors.toMap(OrderItem::getProductId, OrderItem::getQuantity,Integer::sum));


        List<StockedProduct> productsInStock = productClientPort.getProducts(new ArrayList<>(quantityMap.keySet()));

        if (productsInStock.size() != quantityMap.size())
            throw new IllegalArgumentException("Some products are not in stock");

        List<OrderedItem> orderedItems = mapToOrderedItems(productsInStock, quantityMap);

        Order order = buildOrder(userId, orderedItems);
        order.calculateTotalPrice();

            BigDecimal amountAfterDiscount =  Optional.ofNullable(couponCode).filter(StringUtils::hasText)
                            .map(code -> discountRepoClient.discount(userId, couponCode, order.getTotalPrice()))
                            .orElse(order.getTotalPrice());
              order.setTotalWithDiscount(amountAfterDiscount);

        Integer orderId = orderRepoPort.create(order);
        OrderDetails orderDetails = new OrderDetails(orderId,userId, paymentToken, orderedItems, amountAfterDiscount,couponCode);
        publisher.publishEvent(new OrderPlacedEvent(orderDetails));
        return orderId;
    }

    private static List<OrderedItem> mapToOrderedItems(List<StockedProduct> productsInStock, Map<Integer, Integer> quantityMap) {
        return productsInStock
                .stream()
                .map(product ->
                {
                    Integer itemQuantity = quantityMap.getOrDefault(product.productId(), 0);
                    return new OrderedItem(itemQuantity, product.productId(), product.price()
                    );
                })
                .toList();
    }

    @Transactional
    @Override
    public Integer placeOrder(Integer userId, List<OrderItem> orderItems, String paymentToken){
        return placeOrder(userId, orderItems, paymentToken, null);
    }

    @Transactional
    @Override
    public void updateStatus(Integer orderId, OrderStatus orderStatus) {
        orderRepoPort.updateStatus(orderId, orderStatus);
    }

    @Transactional
    @Override
    public void cancelOrder(Integer orderId, String message) {
        orderRepoPort.updateStatus(orderId, OrderStatus.CANCELLED);
        publisher.publishEvent(new OrderCanceledEvent(orderId,message));
    }

    @Override
    public void saveCouponUsage(Integer orderId, String couponCode, Integer userId) {

        discountRepoClient.saveCouponUsage(userId,orderId,couponCode);
    }

    private  Order buildOrder(Integer userId, List<OrderedItem> orderItems) {
        Order order = new Order();
        order.setUserId(userId);
        order.setItems(orderItems);
        order.setStatus(OrderStatus.PENDING);
        return order;
    }



}
