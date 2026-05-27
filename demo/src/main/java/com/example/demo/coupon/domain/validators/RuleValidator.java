package com.example.demo.coupon.domain.validators;

import com.example.demo.coupon.domain.ApplyCouponRequest;
import com.example.demo.coupon.domain.RuleType;

public interface RuleValidator {

    RuleType getRuleType();

    boolean validate(ApplyCouponRequest applyCouponRequest, String ruleValue);

}
