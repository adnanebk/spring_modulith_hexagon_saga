package com.example.demo.order;

import com.example.demo.common.data.OrderedItem;
import com.example.demo.common.events.OrderCanceledEvent;
import com.example.demo.common.events.OrderShippedEvent;
import com.example.demo.coupon.domain.DiscountType;
import com.example.demo.coupon.domain.RuleType;
import com.example.demo.coupon.infra.adapters.repo.coupon.CouponSpringRepo;
import com.example.demo.coupon.infra.entities.CouponEntity;
import com.example.demo.coupon.infra.entities.CouponRuleEntity;
import com.example.demo.order.application.OrderService;
import com.example.demo.order.domain.OrderStatus;
import com.example.demo.order.infra.adapters.repo.order.OrderSpringRepo;
import com.example.demo.order.infra.entities.OrderEntity;
import com.example.demo.order.ports.in.OrderRequest;
import com.example.demo.stock.infra.adapters.repo.ProductSpringRepo;
import com.example.demo.stock.infra.entities.ProductEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.test.EnableScenarios;
import org.springframework.modulith.test.Scenario;

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

    @Autowired
    private CouponSpringRepo couponSpringRepo;




    @BeforeEach
     void setup() {
        productSpringRepo.deleteAll();
        orderSpringRepo.deleteAll();
    }

    @Test
    public void shouldPlaceOrderSuccess(Scenario scenario) {
        List<OrderedItem> orderItems = createOrderItems();
        Integer userId = 11;

        scenario.stimulate(()->orderService.placeOrder(new OrderRequest(userId, orderItems, "token")))
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
    public void shouldPlaceOrderWithCouponSuccess(Scenario scenario) {
        List<OrderedItem> orderItems = createOrderItems();
        CouponEntity coupon = new CouponEntity();
        String couponCode = "p";
        coupon.setCode(couponCode);
        coupon.setDiscount(2.0);
        coupon.setDiscountType(DiscountType.FIXED);
        CouponRuleEntity ruleEntity = new CouponRuleEntity();
        ruleEntity.setType(RuleType.ONCE_PER_USER);
        coupon.setRules(List.of(ruleEntity));
        Integer userId = 11;
        couponSpringRepo.save(coupon);

        scenario.stimulate(()->orderService.placeOrder(new OrderRequest(userId, orderItems, "token", couponCode)))
                .andWaitForEventOfType(OrderShippedEvent.class)
                .toArriveAndVerify(((e,orderId)->{
                    var savedProducts = productSpringRepo.findAll();
                    Assertions.assertEquals(7,savedProducts.get(0).getAmountInStock());
                    Assertions.assertEquals(7,savedProducts.get(1).getAmountInStock());
                    Optional<OrderEntity> savedOrder = orderSpringRepo.findById(orderId);
                    Assertions.assertTrue( savedOrder.isPresent());
                    Assertions.assertEquals(OrderStatus.COMPLETED, savedOrder.get().getStatus());
                    Assertions.assertEquals(21, savedOrder.get().getTotalPrice().longValue());
                    Assertions.assertEquals(19, savedOrder.get().getTotalWithDiscount().longValue());
                    Assertions.assertEquals(couponCode, savedOrder.get().getCouponCode());
                }));

    }



    @Test
    public void shouldCancelOrderOnPaymentFailure(Scenario scenario) {
        List<OrderedItem> orderItems = createOrderItems();
        Integer userId = 11;
        scenario.stimulate(() -> orderService.placeOrder(new OrderRequest(userId, orderItems, "")))
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


    private List<OrderedItem> createOrderItems() {
        ProductEntity product1 = new ProductEntity("p1", BigDecimal.valueOf(5),10);
        ProductEntity product2= new ProductEntity("p2",BigDecimal.valueOf(2),10);
        return productSpringRepo.saveAll(List.of(product1,product2))
                .stream().map(productEntity -> new OrderedItem(productEntity.getId(),3)).toList();
    }



}