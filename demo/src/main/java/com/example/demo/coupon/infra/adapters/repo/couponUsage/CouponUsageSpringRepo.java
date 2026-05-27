package com.example.demo.coupon.infra.adapters.repo.couponUsage;

import com.example.demo.coupon.domain.CouponUsage;
import com.example.demo.coupon.infra.entities.CouponUsageEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CouponUsageSpringRepo extends CrudRepository<CouponUsageEntity, Integer> {
    List<CouponUsageEntity> findByUserIdAndCouponIdOrderByUsedAtDesc(Integer userId, Integer couponId);
}
