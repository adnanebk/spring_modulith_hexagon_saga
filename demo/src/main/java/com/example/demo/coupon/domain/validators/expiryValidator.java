package com.example.demo.coupon.domain.validators;

import com.example.demo.coupon.domain.ApplyCouponRequest;
import com.example.demo.coupon.domain.RuleType;
import org.springframework.stereotype.Component;

@Component
public class expiryValidator implements RuleValidator {
    @Override
    public RuleType getRuleType() {
        return RuleType.EXPIRATION;
    }

    @Override
    public boolean validate(ApplyCouponRequest applyCouponRequest, String ruleValue) {
        return applyCouponRequest.couponUsage().getUsedAt()
                .isBefore(applyCouponRequest.coupon().getEndDate());
    }
}
