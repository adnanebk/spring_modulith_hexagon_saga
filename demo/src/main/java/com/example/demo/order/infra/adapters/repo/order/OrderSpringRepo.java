package com.example.demo.order.infra.adapters.repo.order;

import com.example.demo.order.domain.OrderStatus;
import com.example.demo.order.infra.entities.OrderEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface OrderSpringRepo extends CrudRepository<OrderEntity, Integer> {



    @Transactional
    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query("UPDATE OrderEntity o SET o.status = :status  WHERE o.id= :id")
    void updateStatus(Integer id, OrderStatus status);

    @Query("SELECT o.couponCode FROM OrderEntity o WHERE o.id = :id")
    Optional<String> findCouponCodeById(Integer id);


    @EntityGraph(attributePaths = "items")
    @Query("SELECT o FROM OrderEntity o WHERE o.userId = :userId")
    List<OrderEntity> findByUserIdJoinItems(Integer userId);
}
