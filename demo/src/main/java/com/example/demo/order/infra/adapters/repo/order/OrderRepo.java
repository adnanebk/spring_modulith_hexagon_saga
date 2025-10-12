package com.example.demo.order.infra.adapters.repo.order;

import com.example.demo.order.domain.models.OrderInput;
import com.example.demo.common.enums.OrderStatus;
import com.example.demo.order.ports.out.OrderRepoPort;
import com.example.demo.order.infra.entities.OrderEntity;
import com.example.demo.order.infra.adapters.mappers.OrderMapper;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepo implements OrderRepoPort {

    private OrderSpringRepo orderSpringRepo;
    private OrderMapper orderMapper;

    public OrderRepo(OrderSpringRepo orderSpringRepo, OrderMapper orderMapper) {
        this.orderSpringRepo = orderSpringRepo;
        this.orderMapper = orderMapper;
    }


    @Override
    public Integer create(OrderInput order) {
        OrderEntity entity = orderMapper.toEntity(order);
        return  orderSpringRepo.save(entity).getId();
    }

    @Override
    public void update(Integer id, OrderStatus status, Integer paymentId, Integer shippingId) {
        orderSpringRepo.updateStatus(id, status,paymentId,shippingId);
    }


}
