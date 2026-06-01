package com.example.demo.order;

import com.example.demo.common.data.OrderedItem;
import com.example.demo.common.events.OrderPaymentFailedEvent;
import com.example.demo.common.events.OrderShippedEvent;
import com.example.demo.order.application.OrderService;
import com.example.demo.order.ports.in.OrderRequest;
import com.example.demo.order.ports.out.DiscountClientPort;
import com.example.demo.order.ports.out.OrderRepoPort;
import com.example.demo.order.ports.out.ProductClientPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.List;


@ApplicationModuleTest
class OrderServiceTestInt {

    @Autowired
    private OrderService orderService;

    @MockitoBean
    private  OrderRepoPort orderRepoPort;
    @MockitoBean
    private  ProductClientPort productClientPort;
    @MockitoBean
    private  ApplicationEventPublisher publisher;
    @MockitoBean
    private  DiscountClientPort discountClientPort;





    @Test
    public void shouldPlaceOrderSuccess(Scenario scenario) {
        List<OrderedItem> orderItems = createOrderItems();
        Integer userId = 11;

        scenario.stimulate(()->orderService.placeOrder(new OrderRequest(userId, orderItems, "token")))
                .andWaitForEventOfType(OrderShippedEvent.class)
                .toArriveAndVerify(((e,orderId)->{

                }));
    }

    @Test
    public void shouldPlaceOrderWithCouponSuccess(Scenario scenario) {
        List<OrderedItem> orderItems = createOrderItems();
        Integer userId = 11;
        String couponCode = "p";

        scenario.stimulate(()->orderService.placeOrder(new OrderRequest(userId, orderItems, "token", couponCode)))
                .andWaitForEventOfType(OrderShippedEvent.class)
                .toArriveAndVerify(((e,orderId)->{

                }));

    }



    @Test
    public void shouldCancelOrderOnPaymentFailure(Scenario scenario) {
        List<OrderedItem> orderItems = createOrderItems();
        Integer userId = 11;
        scenario.stimulate(() -> orderService.placeOrder(new OrderRequest(userId, orderItems, "")))
                .andWaitForEventOfType(OrderPaymentFailedEvent.class)
                .toArriveAndVerify((e, orderId) -> {

                });
    }


    private List<OrderedItem> createOrderItems() {
        OrderedItem item1 = new OrderedItem(2, 3, BigDecimal.valueOf(15));
        OrderedItem item2 = new OrderedItem(3, 1, BigDecimal.valueOf(10));
        return List.of(item1, item2);
    }



}