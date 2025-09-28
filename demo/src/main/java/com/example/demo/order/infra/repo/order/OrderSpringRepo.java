package com.example.demo.order.infra.repo.order;

import com.example.demo.common.enums.OrderStatus;
import com.example.demo.order.infra.entities.OrderEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


public interface OrderSpringRepo extends CrudRepository<OrderEntity, Integer> {



    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE OrderEntity o SET o.status = :status , o.paymentId = :paymentId, o.shippingId = :shippingId WHERE o.id= :status")
    void updateStatus(Integer id, OrderStatus status, Integer paymentId, Integer shippingId);
}
