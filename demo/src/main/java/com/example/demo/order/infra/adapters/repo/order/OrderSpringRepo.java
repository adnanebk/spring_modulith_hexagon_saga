package com.example.demo.order.infra.adapters.repo.order;

import com.example.demo.order.domain.OrderStatus;
import com.example.demo.order.infra.entities.OrderEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface OrderSpringRepo extends CrudRepository<OrderEntity, Integer> {



    @Transactional
    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query("UPDATE OrderEntity o SET o.status = :status  WHERE o.id= :id")
    void updateStatus(Integer id, OrderStatus status);
}
