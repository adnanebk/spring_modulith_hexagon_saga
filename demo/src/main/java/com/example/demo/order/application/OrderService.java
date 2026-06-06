package com.example.demo.order.application;


import com.example.demo.common.data.OrderDetails;
import com.example.demo.common.data.OrderedItem;
import com.example.demo.common.events.OrderCanceledEvent;
import com.example.demo.common.events.OrderPlacedEvent;
import com.example.demo.common.exceptions.BusinessException;
import com.example.demo.coupon.domain.CouponCodeUsage;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        validate(orderRequest);

        List<ProductInStock> productsInStock = getProductsInStock(orderRequest.orderItems());

        Map<Integer, ProductInStock> productsInStockMap = productsInStock.stream()
                .collect(Collectors.toMap(ProductInStock::productId, Function.identity()));

        validateItemsAvailability(productsInStockMap, orderRequest.orderItems());

        List<OrderedItem> orderItems = addPriceToItems(orderRequest.orderItems(), productsInStockMap);

        Order order = Order.create(orderRequest.userId(), orderItems);

        applyDiscountIfExist(orderRequest.couponCode(), order);

        Integer orderId = orderRepoPort.create(order);
        order.setId(orderId);
        OrderDetails orderDetails = new OrderDetails(orderId, orderRequest.userId(), orderRequest.paymentToken(), orderItems);
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
    public void completeOrder(Integer orderId, Integer userId) {
        orderRepoPort.updateStatus(orderId, OrderStatus.COMPLETED);
        orderRepoPort.findCouponCodeById(orderId).ifPresent(coupon->{
            discountClientPort.saveCouponUsage(new CouponCodeUsage(userId, orderId, coupon));
        });
    }
    private void validate(OrderRequest orderRequest) {

        if (CollectionUtils.isEmpty(orderRequest.orderItems()))
            throw new BusinessException("Order must contain at least one item");
        // check of duplicate items
        if (orderRequest.orderItems().stream().map(OrderedItem::productId).distinct().count() != orderRequest.orderItems().size())
            throw new BusinessException("Order must contains unique items");
    }

    private void applyDiscountIfExist(String couponCode, Order order) {
        if(StringUtils.hasText(couponCode)){
            BigDecimal amountAfterDiscount =  discountClientPort.discount(order.getUserId(), couponCode, order.getTotalPrice());
            order.applyDiscount(amountAfterDiscount, couponCode);
        }
    }

    private List<ProductInStock> getProductsInStock(List<OrderedItem> orderItems) {
        Map<Integer, Integer> productQuantityMap = orderItems.stream()
                .collect(Collectors.toMap(OrderedItem::productId, OrderedItem::quantity,Integer::sum));
        return productClientPort.getProductsByIds(new ArrayList<>(productQuantityMap.keySet()));
    }

    private  List<OrderedItem> addPriceToItems(List<OrderedItem> orderItems,  Map<Integer, ProductInStock> productsMap) {
        return orderItems.stream().map(item ->
                item.withPrice(productsMap.get(item.productId()).price())).toList();
    }


    private void validateItemsAvailability(Map<Integer, ProductInStock> productsMap, List<OrderedItem> orderItems) {
        Optional<OrderedItem> searchedItem = orderItems.stream().filter(item -> item.quantity() >
                Optional.ofNullable(productsMap.get(item.productId()))
                                .map(ProductInStock::quantity).orElse(0)).findFirst();
        if(searchedItem.isPresent())
            throw new BusinessException("Product " + searchedItem.get().productId() + " is not in stock");
    }


}
