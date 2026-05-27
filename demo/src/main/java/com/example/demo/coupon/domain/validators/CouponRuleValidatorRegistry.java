package com.example.demo.coupon.domain.validators;

import com.example.demo.coupon.domain.RuleType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class CouponRuleValidatorRegistry {



    private final Map<RuleType, RuleValidator> map = new EnumMap<>(RuleType.class);


    public CouponRuleValidatorRegistry(List<RuleValidator> validators) {
        validators.forEach(v -> map.put(v.getRuleType(), v));
    }

    public RuleValidator getValidator(RuleType ruleType) {
        return map.get(ruleType);
    }

}
