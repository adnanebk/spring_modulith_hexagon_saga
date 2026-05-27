package com.example.demo.coupon.domain.validators;

import com.example.demo.coupon.domain.ApplyCouponRequest;
import com.example.demo.coupon.domain.RuleType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MinAmountValidator implements RuleValidator {
    @Override
    public RuleType getRuleType() {
        return RuleType.MINIMUM_AMOUNT;
    }

    @Override
    public boolean validate(ApplyCouponRequest applyCouponRequest, String ruleValue) {
        return applyCouponRequest.totalAmount().compareTo(new BigDecimal(ruleValue)) >= 0;

    }
}
