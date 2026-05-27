package com.example.demo.coupon.infra.adapters.repo.coupon;

import com.example.demo.coupon.infra.entities.CouponEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CouponSpringRepo extends CrudRepository<CouponEntity, Integer> {

    @EntityGraph(attributePaths = {"rules"})
    Optional<CouponEntity> findByCode(String code);
}
