package com.example.demo.order.infra.adapters.repo.order;

import com.example.demo.order.domain.Order;
import com.example.demo.order.domain.OrderStatus;
import com.example.demo.order.infra.adapters.mappers.OrderMapper;
import com.example.demo.order.infra.entities.OrderEntity;
import com.example.demo.order.ports.out.OrderRepoPort;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class OrderRepo implements OrderRepoPort {

    private OrderSpringRepo orderSpringRepo;
    private OrderMapper orderMapper;

    public OrderRepo(OrderSpringRepo orderSpringRepo, OrderMapper orderMapper) {
        this.orderSpringRepo = orderSpringRepo;
        this.orderMapper = orderMapper;
    }


    @Override
    @CacheEvict(value = "orders", key = "#order.userId")
    public Integer create(Order order) {
        OrderEntity entity = orderMapper.toEntity(order);
        return  orderSpringRepo.save(entity).getId();
    }

    @Override
    public void updateStatus(Integer id, OrderStatus status) {
        orderSpringRepo.updateStatus(id, status);
    }

    @Override
    public Optional<String> findCouponCodeById(Integer id) {
        return orderSpringRepo.findCouponCodeById(id);
    }

    @Override
    @Cacheable(value = "orders", key = "#userId")
    public List<Order> findByUserId(Integer userId) {
        return  orderSpringRepo.findByUserIdJoinItems(userId).stream().map(orderMapper::toModel).toList();
    }


}
