package com.example.demo.coupon.infra.adapters.repo.couponUsage;

import com.example.demo.coupon.infra.entities.CouponUsageEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CouponUsageSpringRepo extends CrudRepository<CouponUsageEntity, Integer> {
    List<CouponUsageEntity> findByUserIdAndCouponIdOrderByUsedAtDesc(Integer userId, Integer couponId);
}
