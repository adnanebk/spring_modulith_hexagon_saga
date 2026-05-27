package com.example.demo.coupon.domain.validators;

import com.example.demo.coupon.domain.ApplyCouponRequest;
import com.example.demo.coupon.domain.RuleType;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;

@Component
public class expirationValidator implements RuleValidator {

    private final Clock clock;

    public expirationValidator(Clock clock) {
        this.clock = clock;
    }

    @Override
    public RuleType getRuleType() {
        return RuleType.EXPIRATION;
    }

    @Override
    public boolean validate(ApplyCouponRequest applyCouponRequest, String ruleValue) {
        LocalDate endDate = applyCouponRequest.coupon().getEndDate();
        return !LocalDate.now(clock)
                .isAfter(endDate);
    }
}
