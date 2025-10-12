package com.example.demo.order;

import com.example.demo.common.enums.PaymentType;
import com.example.demo.common.events.OrderCanceledEvent;
import com.example.demo.common.events.OrderShippedEvent;
import com.example.demo.common.models.OrderInput;
import com.example.demo.common.models.OrderItem;
import com.example.demo.common.models.OrderPayment;
import com.example.demo.common.models.OrderShipping;
import com.example.demo.order.application.OrderService;
import com.example.demo.order.domain.models.OrderStatus;
import com.example.demo.order.ports.out.OrderRepoPort;
import com.example.demo.shipping.infra.EmailServiceImp;
import com.example.demo.stock.infra.entities.ProductEntity;
import com.example.demo.stock.infra.repo.ProductSpringRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.test.EnableScenarios;
import org.springframework.modulith.test.Scenario;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;


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
    private EmailServiceImp emailServiceImp;


    @Test
    @Order(1)
    public void testPlaceOrderSuccess(Scenario scenario)  {
        var products = createProducts();
        productSpringRepo.saveAll(products);
        OrderInput order = createOrder();
        doThrow(RuntimeException.class).when(emailServiceImp).sendMessage(any(),any(),any());
        scenario.stimulate(()->orderService.placeOrder(order))
                .andWaitForEventOfType(OrderShippedEvent.class)
                .toArriveAndVerify((e->{
                    verify(orderRepoPort).update(1,OrderStatus.COMPLETED,1,1);
                    var savedProducts = productSpringRepo.findAll();
                    Assertions.assertEquals(2, savedProducts.size());
                    Assertions.assertEquals(8,savedProducts.get(0).getAmountInStock());
                }));
    }
    @Test
    @Order(2)
    public void testPlaceOrderPaymentFailed(Scenario scenario)  {
        var products = createProducts();
        productSpringRepo.saveAll(products);
        OrderInput order = createOrder();
        order.getPaymentInfo().setType(PaymentType.PAYPAL);
        scenario.stimulate(()->orderService.placeOrder(order))
                .andWaitForEventOfType(OrderCanceledEvent.class)
                .toArriveAndVerify((e->{
                    verify(orderRepoPort).update(1,OrderStatus.CANCELLED,null,null);
                    var savedProducts = productSpringRepo.findAll();
                    Assertions.assertTrue(savedProducts.size()==2);
                    Assertions.assertEquals(10,savedProducts.get(0).getAmountInStock());
                }));
    }
    @Test
    @Order(3)
    public void testItemStockFailed(Scenario scenario)  {
        var products = createProducts();
        products.get(0).setAmountInStock(1);
        productSpringRepo.saveAll(products);
        OrderInput order = createOrder();
        order.getPaymentInfo().setType(PaymentType.CASH);
        scenario.stimulate(()->orderService.placeOrder(order))
                .andWaitForEventOfType(OrderCanceledEvent.class)
                .toArriveAndVerify((e->{
                    verify(orderRepoPort).update(1,OrderStatus.CANCELLED,null,null);
                    var savedProducts = productSpringRepo.findAll();
                    Assertions.assertTrue(savedProducts.size()==2);
                    Assertions.assertEquals(1,savedProducts.get(0).getAmountInStock());
                }));
    }
    private  OrderInput createOrder() {
        OrderItem i1 = new OrderItem("",10.0,2,1);
        OrderItem i2 = new OrderItem("",10.0,2,2);
        OrderInput order = new OrderInput();
        order.setItems(List.of(i1,i2));
        order.setTotal(10.0);
        order.setShipping(new OrderShipping());
        order.setPaymentInfo(new OrderPayment(PaymentType.CASH));
        return order;
    }

    private List<ProductEntity> createProducts() {
        ProductEntity product1 = new ProductEntity("p1",10);
        ProductEntity product2= new ProductEntity("p2",10);
        return List.of(product1,product2);
    }







}