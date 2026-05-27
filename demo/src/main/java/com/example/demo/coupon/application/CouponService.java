package com.example.demo.coupon.application;

import com.example.demo.coupon.domain.AppliedCouponSummary;
import com.example.demo.coupon.domain.ApplyCouponRequest;
import com.example.demo.coupon.domain.Coupon;
import com.example.demo.coupon.domain.CouponUsage;
import com.example.demo.coupon.domain.validators.CouponRuleValidatorRegistry;
import com.example.demo.coupon.ports.CouponRepositoryPort;
import com.example.demo.coupon.ports.CouponServicePort;
import com.example.demo.coupon.ports.CouponUsageRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
public class CouponService implements CouponServicePort {

    private final CouponRepositoryPort couponRepositoryPort;
    private final CouponRuleValidatorRegistry validatorRegistry;
    private final CouponUsageRepositoryPort couponUsageRepositoryPort;

    public CouponService(CouponRepositoryPort couponRepositoryPort, CouponRuleValidatorRegistry validatorRegistry, CouponUsageRepositoryPort couponUsageRepositoryPort) {
        this.couponRepositoryPort = couponRepositoryPort;
        this.validatorRegistry = validatorRegistry;
        this.couponUsageRepositoryPort = couponUsageRepositoryPort;
    }

    @Override
    public AppliedCouponSummary applyCoupon(
            Integer userId,
            String couponCode,
            BigDecimal totalAmount
    ) {

        Coupon coupon = couponRepositoryPort.findByCode(couponCode)
                .orElseThrow(() -> new IllegalArgumentException("Coupon code not found"));

        CouponUsage couponUsage =
                couponUsageRepositoryPort
                        .findLastUsageByUserIdAndCouponId(userId, coupon.getId())
                        .orElseGet(() -> new CouponUsage(userId, coupon.getId()));

        ApplyCouponRequest request =
                new ApplyCouponRequest(coupon, couponUsage, totalAmount);

        validateCouponEligibility(coupon, request);

        BigDecimal finalAmount =
                coupon.calculateFinalAmount(totalAmount);

        return new AppliedCouponSummary(
                finalAmount,
                totalAmount,
                coupon.getDiscountType(),
                couponUsage
        );
    }

    @Override
    public void saveCouponUsage(Integer userId, Integer orderId, String couponCode) {
        Integer couponId = couponRepositoryPort.findByCode(couponCode)
                .orElseThrow(() -> new IllegalArgumentException("Coupon code not found")).getId();
        CouponUsage couponUsage = new CouponUsage(couponId,orderId,userId);
        couponUsageRepositoryPort.save(couponUsage);
    }


    private void validateCouponEligibility(Coupon coupon, ApplyCouponRequest request
    ) {

        boolean valid = coupon.getRules()
                .stream()
                .allMatch(rule ->
                        validatorRegistry
                                .getValidator(rule.getType())
                                .validate(request, rule.getValue())
                );

        if (!valid) {
            throw new IllegalStateException(
                    "Coupon conditions are not met."
            );
        }
    }
}
