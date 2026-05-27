package com.example.demo.order;

import com.example.demo.common.events.OrderCanceledEvent;
import com.example.demo.common.events.OrderShippedEvent;
import com.example.demo.order.application.OrderService;
import com.example.demo.order.domain.OrderItem;
import com.example.demo.order.domain.OrderStatus;
import com.example.demo.order.infra.adapters.repo.order.OrderSpringRepo;
import com.example.demo.order.infra.entities.OrderEntity;
import com.example.demo.order.ports.out.OrderRepoPort;
import com.example.demo.stock.infra.entities.ProductEntity;
import com.example.demo.stock.infra.adapters.repo.ProductSpringRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.test.EnableScenarios;
import org.springframework.modulith.test.Scenario;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@EnableScenarios
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductSpringRepo productSpringRepo;

    @Autowired
    private OrderSpringRepo orderSpringRepo;

    @MockitoSpyBean
    private  OrderRepoPort orderRepoPort;



    @BeforeEach
     void setup() {
        productSpringRepo.deleteAll();
        orderSpringRepo.deleteAll();
    }

    @Test
    public void shouldPlaceOrderSuccess(Scenario scenario) {
        List<OrderItem> orderItems = createOrderItems();
        Integer userId = 11;

        scenario.stimulate(()->orderService.placeOrder(userId,orderItems,"p"))
                .andWaitForEventOfType(OrderShippedEvent.class)
                .toArriveAndVerify(((e,orderId)->{
                    var savedProducts = productSpringRepo.findAll();
                    Assertions.assertEquals(7,savedProducts.get(0).getAmountInStock());
                    Assertions.assertEquals(7,savedProducts.get(1).getAmountInStock());
                    Optional<OrderEntity> savedOrder = orderSpringRepo.findById(orderId);
                    Assertions.assertTrue( savedOrder.isPresent());
                    Assertions.assertEquals(OrderStatus.COMPLETED, savedOrder.get().getStatus());
                    Assertions.assertEquals(21, savedOrder.get().getTotalPrice().longValue());
                }));
    }



    @Test
    public void shouldCancelOrderOnPaymentFailure(Scenario scenario) {
        List<OrderItem> orderItems = createOrderItems();
        Integer userId = 11;
        scenario.stimulate(() -> orderService.placeOrder(userId, orderItems, ""))
                .andWaitForEventOfType(OrderCanceledEvent.class)
                .toArriveAndVerify((e, orderId) -> {
                    var savedProducts = productSpringRepo.findAll();
                    Assertions.assertEquals(10, savedProducts.get(0).getAmountInStock());
                    Assertions.assertEquals(10, savedProducts.get(1).getAmountInStock());
                    Optional<OrderEntity> savedOrder = orderSpringRepo.findById(orderId);
                    Assertions.assertTrue(savedOrder.isPresent());
                    Assertions.assertEquals(OrderStatus.CANCELLED, savedOrder.get().getStatus());
                });
    }


    private List<OrderItem> createOrderItems() {
        ProductEntity product1 = new ProductEntity("p1", BigDecimal.valueOf(5),10);
        ProductEntity product2= new ProductEntity("p2",BigDecimal.valueOf(2),10);
        return productSpringRepo.saveAll(List.of(product1,product2))
                .stream().map(productEntity -> new OrderItem(productEntity.getId(),3)).toList();
    }



}