package com.example.demo.order.infra.adapters.repo.order;

import com.example.demo.order.domain.models.OrderInput;
import com.example.demo.order.domain.models.OrderStatus;
import com.example.demo.order.infra.adapters.mappers.OrderMapper;
import com.example.demo.order.infra.entities.OrderEntity;
import com.example.demo.order.ports.out.OrderRepoPort;
import org.springframework.stereotype.Repository;

import java.util.UUID;


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
    public void updateStatus(Integer id, OrderStatus status) {
        orderSpringRepo.updateStatus(id, status);
    }

    @Override
    public void update(Integer id, OrderStatus orderStatus, UUID locationId, UUID transactionId) {
        orderSpringRepo.update(id, orderStatus,locationId,transactionId);
    }


}
