package com.example.demo.order.infra.adapters.mappers;



import com.example.demo.order.domain.models.Order;
import com.example.demo.order.domain.models.OrderInput;
import com.example.demo.order.domain.models.OrderPayment;
import com.example.demo.order.domain.models.OrderShipping;
import com.example.demo.order.infra.dto.OrderInputDto;
import com.example.demo.order.infra.entities.OrderEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {
    private ItemMapper itemMapper;

    public OrderMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public OrderEntity toEntity(OrderInput order) {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(order, orderEntity);
        orderEntity.setItems(order.items().stream().map(itemMapper::toEntity).collect(Collectors.toList()));
        return orderEntity;
    }

    public Order toDomain(OrderInputDto orderInputDto) {
        Order order = new Order();
        BeanUtils.copyProperties(orderInputDto, order);
        order.setPaymentInfo(new OrderPayment(orderInputDto.paymentType()));
        order.setShipping(new OrderShipping(orderInputDto.customerName(), orderInputDto.customerEmail(), orderInputDto.customerAdress(), orderInputDto.customerPhone()));
        order.setItems(orderInputDto.items().stream().map(itemMapper::toDomain).collect(Collectors.toList()));
        return order;
    }
}
