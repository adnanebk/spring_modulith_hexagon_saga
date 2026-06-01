package com.example.demo.coupon.domain.validators;

import com.example.demo.coupon.domain.ApplyCouponRequest;
import com.example.demo.coupon.domain.CouponRule;
import com.example.demo.coupon.domain.CouponUsage;
import com.example.demo.coupon.domain.RuleType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class CouponRuleValidator {


    private final Map<RuleType, RuleValidator> map = new EnumMap<>(RuleType.class);


    public CouponRuleValidator(List<RuleValidator> validators) {
        validators.forEach(v -> map.put(v.getRuleType(), v));
    }

    public boolean validate(CouponRule couponRule, ApplyCouponRequest applyCouponRequest) {
        return map.get(couponRule.getType()).validate(applyCouponRequest, couponRule.getValue());
    }

}
