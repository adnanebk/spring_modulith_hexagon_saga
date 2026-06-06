package com.example.demo.coupon.infra.adapters.repo.coupon;

import com.example.demo.coupon.infra.entities.CouponEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponSpringRepo extends JpaRepository<CouponEntity, Integer> {

    @EntityGraph(attributePaths = {"rules"})
    Optional<CouponEntity> findByCode(String code);
}
