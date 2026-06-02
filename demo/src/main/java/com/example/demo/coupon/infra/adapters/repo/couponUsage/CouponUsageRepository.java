package com.example.demo.coupon.infra.adapters.repo.couponUsage;

import com.example.demo.coupon.domain.CouponUsage;
import com.example.demo.coupon.infra.adapters.mappers.CouponUsageMapper;
import com.example.demo.coupon.ports.CouponUsageRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CouponUsageRepository implements CouponUsageRepositoryPort {

    private final CouponUsageSpringRepo couponUsageSpringRepo;
    private final CouponUsageMapper couponUsageMapper;

    public CouponUsageRepository(CouponUsageSpringRepo couponUsageSpringRepo, CouponUsageMapper couponUsageMapper) {
        this.couponUsageSpringRepo = couponUsageSpringRepo;
        this.couponUsageMapper = couponUsageMapper;
    }

    @Override
    public List<CouponUsage> findAllByUserIdAndCouponId(Integer userId, Integer couponId) {
        return couponUsageSpringRepo.findByUserIdAndCouponIdOrderByUsedAtDesc(userId, couponId)
                .stream().map(couponUsageMapper::toDomain).toList();
    }

    @Override
    public void save(CouponUsage couponUsage) {

        couponUsageSpringRepo.save(couponUsageMapper.toEntity(couponUsage));
    }
}
