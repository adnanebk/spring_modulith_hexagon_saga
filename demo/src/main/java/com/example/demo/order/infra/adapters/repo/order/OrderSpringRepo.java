package com.example.demo.order.infra.adapters.repo.order;

import com.example.demo.common.enums.OrderStatus;
import com.example.demo.order.infra.entities.OrderEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


public interface OrderSpringRepo extends CrudRepository<OrderEntity, Integer> {



    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE OrderEntity o SET o.status = :status  WHERE o.id= :id")
    void updateStatus(Integer id, OrderStatus status);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE OrderEntity o SET o.status = :status , o.locationId = :locationId, o.transactionId = :transactionId WHERE o.id= :id")
    void update(Integer id, OrderStatus status, UUID locationId, UUID transactionId);
}
