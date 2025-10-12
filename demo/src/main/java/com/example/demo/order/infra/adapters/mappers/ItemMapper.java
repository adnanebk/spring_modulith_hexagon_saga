package com.example.demo.order.infra.adapters.mappers;


import com.example.demo.common.models.OrderItem;
import com.example.demo.order.infra.dto.OrderItemInputDto;
import com.example.demo.order.infra.entities.ItemEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public ItemEntity toEntity(OrderItem item){
       ItemEntity itemEntity = new ItemEntity();
       BeanUtils.copyProperties(item, itemEntity);
       return itemEntity;
   }

   public OrderItem toModel(ItemEntity itemEntity){
       OrderItem item = new OrderItem();
       BeanUtils.copyProperties(itemEntity, item);
       return item;
   }

    public OrderItem toDomain(OrderItemInputDto orderItemInputDto) {
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(orderItemInputDto, orderItem);
        return orderItem;
    }
}
