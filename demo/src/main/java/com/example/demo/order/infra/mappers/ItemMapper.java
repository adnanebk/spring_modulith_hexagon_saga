package com.example.demo.order.infra.mappers;


import com.example.demo.common.models.OrderItem;
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

}
