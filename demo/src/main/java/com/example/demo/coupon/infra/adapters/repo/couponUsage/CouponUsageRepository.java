package com.example.demo.coupon.infra.adapters.repo.couponUsage;

import com.example.demo.coupon.domain.CouponUsage;
import com.example.demo.coupon.infra.adapters.mappers.CouponUsageMapper;
import com.example.demo.coupon.infra.adapters.repo.coupon.CouponSpringRepo;
import com.example.demo.coupon.infra.entities.CouponUsageEntity;
import com.example.demo.coupon.ports.CouponUsageRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CouponUsageRepository implements CouponUsageRepositoryPort {

    private final CouponUsageSpringRepo couponUsageSpringRepo;
    private final CouponSpringRepo couponSpringRepo;
    private final CouponUsageMapper couponUsageMapper;

    public CouponUsageRepository(CouponUsageSpringRepo couponUsageSpringRepo, CouponSpringRepo couponSpringRepo, CouponUsageMapper couponUsageMapper) {
        this.couponUsageSpringRepo = couponUsageSpringRepo;
        this.couponSpringRepo = couponSpringRepo;
        this.couponUsageMapper = couponUsageMapper;
    }

    @Override
    public List<CouponUsage> findAllByUserIdAndCouponId(Integer userId, Integer couponId) {
        return couponUsageSpringRepo.findByUserIdAndCouponIdOrderByUsedAtDesc(userId, couponId)
                .stream().map(couponUsageMapper::toDomain).toList();
    }

    @Override
    public void save(CouponUsage couponUsage) {

        CouponUsageEntity entity = couponUsageMapper.toEntity(couponUsage);
        entity.setCoupon(couponSpringRepo.getReferenceById(couponUsage.getCouponId()));
        couponUsageSpringRepo.save(entity);
    }
}
