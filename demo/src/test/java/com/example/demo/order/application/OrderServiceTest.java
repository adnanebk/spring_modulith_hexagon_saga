package com.example.demo.order.application;


import com.example.demo.common.data.OrderedItem;
import com.example.demo.common.data.ProductInStock;
import com.example.demo.common.events.OrderPlacedEvent;
import com.example.demo.order.ports.in.OrderRequest;
import com.example.demo.order.ports.out.DiscountClientPort;
import com.example.demo.order.ports.out.OrderRepoPort;
import com.example.demo.order.ports.out.ProductClientPort;
import com.example.demo.order.domain.Order;
import com.example.demo.order.domain.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.ArgumentCaptor;

@ApplicationModuleTest
class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @MockitoBean
    private OrderRepoPort orderRepoPort;
    @MockitoBean
    private ProductClientPort productClientPort;

    @MockitoBean
    private DiscountClientPort discountClientPort;

    ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);





    @Test
    public void shouldPlaceOrderSuccess(Scenario scenario) {
        List<OrderedItem> orderItems = createOrderItems();
        Integer userId = 11;

        when(productClientPort.getProductsByIds(anyList()))
                .thenReturn(List.of(
                        new ProductInStock(2, BigDecimal.valueOf(15), 10),
                        new ProductInStock(3, BigDecimal.valueOf(10), 5)
                ));
        when(orderRepoPort.create(any())).thenReturn(1);

        scenario.stimulate(()->orderService.placeOrder(new OrderRequest(userId, orderItems, "token")))
                .andWaitForEventOfType(OrderPlacedEvent.class)
                .toArriveAndVerify(((e,orderId)->{
                    assertNotNull(e.orderDetails());
                    assertEquals(userId, e.orderDetails().userId());
                    assertEquals("token", e.orderDetails().paymentToken());
                    assertEquals(2, e.orderDetails().items().size());
                    assertEquals(orderId, e.orderDetails().orderId());
                    assertEquals(11, e.orderDetails().userId());

                }));

        verify(orderRepoPort).create(orderCaptor.capture());
        Order capturedOrder = orderCaptor.getValue();
        assertEquals(userId, capturedOrder.getUserId());
        assertEquals(2, capturedOrder.getItems().size());
        assertEquals(BigDecimal.valueOf(65), capturedOrder.getTotalPrice());
        assertNull(capturedOrder.getTotalBeforeDiscount());
        assertEquals(OrderStatus.PENDING, capturedOrder.getStatus());
        assertNull(capturedOrder.getCouponCode());
    }

    @Test
    public void shouldPlaceOrderWithCouponSuccess(Scenario scenario) {
        List<OrderedItem> orderItems = createOrderItems();
        Integer userId = 11;
        String couponCode = "p";

        when(productClientPort.getProductsByIds(anyList()))
                .thenReturn(List.of(
                        new ProductInStock(2, BigDecimal.valueOf(15), 10),
                        new ProductInStock(3, BigDecimal.valueOf(10), 5)
                ));
        when(orderRepoPort.create(any())).thenReturn(1);
        when(discountClientPort.discount(eq(userId), eq(couponCode), any()))
                .thenReturn(BigDecimal.valueOf(50));

        scenario.stimulate(()->orderService.placeOrder(new OrderRequest(userId, orderItems, "token", couponCode)))
                .andWaitForEventOfType(OrderPlacedEvent.class)
                .toArriveAndVerify(((e,orderId)->{
                    assertNotNull(e.orderDetails());
                    assertEquals(userId, e.orderDetails().userId());
                    assertEquals("token", e.orderDetails().paymentToken());
                    assertEquals(2, e.orderDetails().items().size());
                }));
        verify(orderRepoPort).create(orderCaptor.capture());
        Order capturedOrder = orderCaptor.getValue();
        assertEquals(userId, capturedOrder.getUserId());
        assertEquals(couponCode, capturedOrder.getCouponCode());
        assertEquals(2, capturedOrder.getItems().size());
        assertEquals(BigDecimal.valueOf(50), capturedOrder.getTotalPrice());
        assertEquals(BigDecimal.valueOf(65), capturedOrder.getTotalBeforeDiscount());
        assertEquals(OrderStatus.PENDING, capturedOrder.getStatus());
    }



    private List<OrderedItem> createOrderItems() {
        OrderedItem item1 = new OrderedItem(2, 3, BigDecimal.valueOf(15));
        OrderedItem item2 = new OrderedItem(3, 2, BigDecimal.valueOf(10));
        return List.of(item1, item2);
    }
}