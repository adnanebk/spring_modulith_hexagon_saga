package com.example.demo.coupon.domain.validators;

import com.example.demo.coupon.domain.ApplyCouponRequest;
import com.example.demo.coupon.domain.RuleType;
import org.springframework.stereotype.Component;

@Component
public class OncerPerUserValidator implements RuleValidator {
    @Override
    public RuleType getRuleType() {
        return RuleType.ONCE_PER_USER;
    }

    @Override
    public boolean validate(ApplyCouponRequest applyCouponRequest, String ruleValue) {
        return applyCouponRequest.usageHistory().isEmpty();
    }
}
