package com.example.demo.order.infra.repo.order;

import com.example.demo.common.models.OrderInput;
import com.example.demo.order.domain.models.OrderOutput;
import com.example.demo.order.domain.models.OrderStatus;
import com.example.demo.order.ports.out.OrderRepoPort;
import com.example.demo.order.infra.entities.OrderEntity;
import com.example.demo.order.infra.mappers.OrderMapper;
import com.example.demo.order.infra.repo.item.ItemSpringRepo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrderRepo implements OrderRepoPort {

    private OrderSpringRepo orderSpringRepo;
    private ItemSpringRepo itemSpringRepo;
    private OrderMapper orderMapper;

    public OrderRepo(OrderSpringRepo orderSpringRepo, ItemSpringRepo itemSpringRepo, OrderMapper orderMapper) {
        this.orderSpringRepo = orderSpringRepo;
        this.itemSpringRepo = itemSpringRepo;
        this.orderMapper = orderMapper;
    }


    @Override
    @Transactional
    public OrderOutput save(OrderInput order) {
        OrderEntity entity = orderMapper.toEntity(order);
        return  orderMapper.toModel(orderSpringRepo.save(entity));
    }

    @Override
    public void update(Integer id, OrderStatus status, Integer paymentId, Integer shippingId) {
        orderSpringRepo.updateStatus(id, status,paymentId,shippingId);
    }


}
