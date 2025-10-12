package com.example.demo.order.ports.out;

import com.example.demo.common.models.OrderItem;

import java.util.List;

public interface ItemRepoPort  {
    OrderItem save(OrderItem item);

    List<OrderItem> findAllByIds(List<Integer> itemsIds);
}
