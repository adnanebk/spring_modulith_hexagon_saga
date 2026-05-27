package com.example.demo.coupon.infra.adapters.mappers;

import com.example.demo.coupon.domain.CouponUsage;
import com.example.demo.coupon.infra.entities.CouponUsageEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CouponUsageMapper {

    public CouponUsage toDomain(CouponUsageEntity entity) {
        CouponUsage couponUsage = new CouponUsage(entity.getUserId(), entity.getCouponId());
        BeanUtils.copyProperties(entity, couponUsage);
        return couponUsage;
    }

    public CouponUsageEntity toEntity(CouponUsage couponUsage) {
        CouponUsageEntity entity = new CouponUsageEntity();
        BeanUtils.copyProperties(couponUsage, entity);
        return entity;
    }
}
