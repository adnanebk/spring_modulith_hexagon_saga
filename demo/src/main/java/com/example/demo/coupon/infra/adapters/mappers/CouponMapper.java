package com.example.demo.coupon.infra.adapters.mappers;

import com.example.demo.coupon.domain.Coupon;
import com.example.demo.coupon.infra.entities.CouponEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CouponMapper {
    private final CouponRuleMapper couponRuleMapper;

    public CouponMapper(CouponRuleMapper couponRuleMapper) {
        this.couponRuleMapper = couponRuleMapper;
    }

    public Coupon toDomain(CouponEntity entity) {
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(entity, coupon);
        coupon.setRules(entity.getRules().stream().map(couponRuleMapper::toDomain).collect(Collectors.toList()));
        return coupon;
    }

    public CouponEntity toEntity(Coupon coupon) {
        CouponEntity entity = new CouponEntity();
        BeanUtils.copyProperties(coupon, entity);
        entity.setRules(coupon.getRules().stream().map(couponRuleMapper::toEntity).collect(Collectors.toList()));
        return entity;
    }
}
