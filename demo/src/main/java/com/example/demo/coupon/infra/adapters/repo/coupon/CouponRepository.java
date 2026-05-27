package com.example.demo.coupon.infra.adapters.repo.coupon;

import com.example.demo.coupon.domain.Coupon;
import com.example.demo.coupon.infra.adapters.mappers.CouponMapper;
import com.example.demo.coupon.ports.CouponRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CouponRepository implements CouponRepositoryPort {

    private final CouponSpringRepo couponSpringRepo;
    private final CouponMapper couponMapper;

    public CouponRepository(CouponSpringRepo couponSpringRepo, CouponMapper couponMapper) {
        this.couponSpringRepo = couponSpringRepo;
        this.couponMapper = couponMapper;
    }

    @Override
    public Optional<Coupon> findByCode(String couponCode) {
        return couponSpringRepo.findByCode(couponCode).map(couponMapper::toDomain);
    }
}
