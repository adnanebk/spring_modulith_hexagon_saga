package com.example.demo.order;

import com.example.demo.common.enums.PaymentType;
import com.example.demo.common.events.OrderCanceledEvent;
import com.example.demo.common.events.OrderShippedEvent;
import com.example.demo.common.events.data.OrderData;
import com.example.demo.notification.application.NotificationListener;
import com.example.demo.order.domain.models.*;
import com.example.demo.order.application.OrderService;
import com.example.demo.order.domain.models.Order;
import com.example.demo.order.ports.out.OrderRepoPort;
import com.example.demo.stock.infra.entities.ProductEntity;
import com.example.demo.stock.infra.repo.ProductSpringRepo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.test.EnableScenarios;
import org.springframework.modulith.test.Scenario;

import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;


import java.util.List;

import static com.example.demo.common.enums.GeneratedId.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@SpringBootTest
@EnableScenarios
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductSpringRepo productSpringRepo;

    @MockitoSpyBean
    private  OrderRepoPort orderRepoPort;

    @MockitoSpyBean
    private NotificationListener notificationListener;

    @Test
    public void testPlaceOrderSuccess(Scenario scenario)  {
        Order order = createOrder();
        scenario.stimulate(()->orderService.placeOrder(order))
                .andWaitForEventOfType(OrderShippedEvent.class)
                .toArriveAndVerify((e->{
                    OrderData data = e.getData();
                    verify(orderRepoPort).update(data.id(),OrderStatus.COMPLETED,data.generatedIds().get(LOCATION_ID),data.generatedIds().get(PAYMENT_ID));
                    verify(notificationListener).handleNotificationEvent(any());
                    var savedProducts = productSpringRepo.findAll();
                    Assertions.assertEquals(2, savedProducts.size());
                    Assertions.assertEquals(8,savedProducts.get(0).getAmountInStock());
                }));
    }

    @Test
    public void testPlaceOrderPaymentFailed(Scenario scenario)  {
        var products = createProducts();
        Order order = createOrder();
        order.getPaymentInfo().setType(PaymentType.PAYPAL);
        scenario.stimulate(()->orderService.placeOrder(order))
                .andWaitForEventOfType(OrderCanceledEvent.class)
                .toArriveAndVerify((e->{
                    verify(orderRepoPort).updateStatus(any(),eq(OrderStatus.CANCELLED));
                    var savedProducts = productSpringRepo.findAll();
                    Assertions.assertTrue(savedProducts.size()==2);
                    Assertions.assertEquals(10,savedProducts.get(0).getAmountInStock());

                }));
    }

    private Order createOrder() {
        OrderItem i1 = new OrderItem("",10.0,2,1);
        OrderItem i2 = new OrderItem("",10.0,2,2);
        Order order = new Order();
        order.setItems(List.of(i1,i2));
        order.setTotal(10.0);
        order.setShipping(new OrderShipping());
        order.setPaymentInfo(new OrderPayment(PaymentType.CASH));
        return order;
    }

    private List<ProductEntity> createProducts() {
        productSpringRepo.deleteAll();
        ProductEntity product1 = new ProductEntity("p1",10);
        ProductEntity product2= new ProductEntity("p2",10);
        return productSpringRepo.saveAll(List.of(product1,product2));
    }







}