package com.example.demo.coupon.infra.adapters.repo.couponUsage;

import com.example.demo.coupon.infra.entities.CouponUsageEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CouponUsageSpringRepo extends CrudRepository<CouponUsageEntity, Integer> {
    Optional<CouponUsageEntity> findFirstByUserIdAndCouponIdOrderByUsedAtDesc(Integer userId, Integer couponId);
}
