package com.example.demo.coupon.infra.adapters.mappers;

import com.example.demo.coupon.domain.CouponRule;
import com.example.demo.coupon.infra.entities.CouponRuleEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CouponRuleMapper {

    public CouponRule toDomain(CouponRuleEntity entity) {
        CouponRule rule = new CouponRule();
        BeanUtils.copyProperties(entity, rule);
        return rule;
    }

    public CouponRuleEntity toEntity(CouponRule rule) {
        CouponRuleEntity entity = new CouponRuleEntity();
        BeanUtils.copyProperties(rule, entity);
        return entity;
    }
}
