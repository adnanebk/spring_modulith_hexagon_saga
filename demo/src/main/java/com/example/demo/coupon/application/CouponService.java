package com.example.demo.coupon.application;

import com.example.demo.common.exceptions.BusinessException;
import com.example.demo.common.exceptions.ResourceNotFoundException;
import com.example.demo.coupon.domain.AppliedCouponSummary;
import com.example.demo.coupon.domain.Coupon;
import com.example.demo.coupon.domain.CouponUsage;
import com.example.demo.coupon.domain.validators.CouponRuleValidator;
import com.example.demo.coupon.ports.CouponRepositoryPort;
import com.example.demo.coupon.ports.CouponServicePort;
import com.example.demo.coupon.ports.CouponUsageRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CouponService implements CouponServicePort {

    private final CouponRepositoryPort couponRepositoryPort;
    private final CouponRuleValidator couponValidator;
    private final CouponUsageRepositoryPort couponUsageRepositoryPort;

    public CouponService(CouponRepositoryPort couponRepositoryPort, CouponRuleValidator couponValidator, CouponUsageRepositoryPort couponUsageRepositoryPort) {
        this.couponRepositoryPort = couponRepositoryPort;
        this.couponValidator = couponValidator;
        this.couponUsageRepositoryPort = couponUsageRepositoryPort;
    }

    @Override
    public AppliedCouponSummary applyCoupon(
            Integer userId,
            String couponCode,
            BigDecimal totalAmount
    ) {

        Coupon coupon = findCouponByCode(couponCode);
        List<CouponUsage> usageHistory = couponUsageRepositoryPort.findAllByUserIdAndCouponId(userId, coupon.getId());



        validateCouponEligibility(coupon, usageHistory, totalAmount);

        BigDecimal finalAmount =
                coupon.calculateFinalAmount(totalAmount);

        return new AppliedCouponSummary(
                finalAmount,
                totalAmount,
                coupon.getDiscountType()
        );
    }



    @Transactional
    @Override
    public void saveCouponUsage(Integer orderId, Integer userId,String couponCode) {
        if(StringUtils.hasText(couponCode)){
            Integer couponId = findCouponByCode(couponCode).getId();
            CouponUsage couponUsage = new CouponUsage(couponId,orderId,userId);
            couponUsageRepositoryPort.save(couponUsage);
        }

    }


    private void validateCouponEligibility(Coupon coupon, List<CouponUsage> usageHistory, BigDecimal totalAmount
    ) {
        boolean valid = coupon.isEligible(usageHistory,totalAmount, couponValidator);

        if (!valid) {
            throw new BusinessException(
                    "Coupon conditions are not met."
            );
        }
    }
    private Coupon findCouponByCode(String couponCode) {
        return couponRepositoryPort.findByCode(couponCode)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon code not found"));
    }
}
