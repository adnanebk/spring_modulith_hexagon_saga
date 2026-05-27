package com.example.demo.coupon.application;

import com.example.demo.coupon.domain.*;
import com.example.demo.coupon.domain.validators.MinAmountValidator;
import com.example.demo.coupon.domain.validators.OncerPerUserValidator;
import com.example.demo.coupon.domain.validators.CouponRuleValidatorRegistry;
import com.example.demo.coupon.domain.validators.expiryValidator;
import com.example.demo.coupon.ports.CouponRepositoryPort;
import com.example.demo.coupon.ports.CouponUsageRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    private CouponRepositoryPort couponRepositoryPort;

    @Mock
    private CouponUsageRepositoryPort couponUsageRepositoryPort;

    private CouponService couponService;

    @BeforeEach
    void setUp() {
        CouponRuleValidatorRegistry validatorRegistry = new CouponRuleValidatorRegistry(List.of(
                new MinAmountValidator(),
                new OncerPerUserValidator(),
                new expiryValidator()
        ));
        couponService = new CouponService(couponRepositoryPort, validatorRegistry, couponUsageRepositoryPort);
    }

    @Test
    void shouldApplyCoupon_WithFixedDiscount_Successfully() {
        Integer userId = 1;
        String couponCode = "SAVE10";
        BigDecimal totalAmount = new BigDecimal("100.00");

        Coupon coupon = createCoupon(couponCode, 10.0, DiscountType.FIXED);

        when(couponRepositoryPort.findByCode(couponCode)).thenReturn(Optional.of(coupon));
        when(couponUsageRepositoryPort.findLastUsageByUserIdAndCouponId(userId, coupon.getId()))
                .thenReturn(Optional.empty());

        AppliedCouponSummary result = couponService.applyCoupon(userId, couponCode, totalAmount);

        assertNotNull(result);
        assertEquals(new BigDecimal("90.00"), result.totalAmount());
        assertEquals(totalAmount, result.discountAmount());
        assertEquals(DiscountType.FIXED, result.discountType());
    }

    @Test
    void shouldApplyCoupon_WithPercentageDiscount_Successfully() {
        Integer userId = 1;
        String couponCode = "SAVE20PERCENT";
        BigDecimal totalAmount = new BigDecimal("100.00");

        Coupon coupon = createCoupon(couponCode, 20.0, DiscountType.PERCENTAGE);
        CouponUsage couponUsage = new CouponUsage(userId, coupon.getId());

        when(couponRepositoryPort.findByCode(couponCode)).thenReturn(Optional.of(coupon));
        when(couponUsageRepositoryPort.findLastUsageByUserIdAndCouponId(userId, coupon.getId()))
                .thenReturn(Optional.of(couponUsage));

        AppliedCouponSummary result = couponService.applyCoupon(userId, couponCode, totalAmount);

        assertNotNull(result);
        assertEquals(new BigDecimal("80.00"), result.totalAmount());
        assertEquals(totalAmount, result.discountAmount());
        assertEquals(DiscountType.PERCENTAGE, result.discountType());
    }

    @Test
    void shouldThrow_WhenCouponNotFound() {
        Integer userId = 1;
        String couponCode = "INVALID";
        BigDecimal totalAmount = new BigDecimal("100.00");

        when(couponRepositoryPort.findByCode(couponCode)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> couponService.applyCoupon(userId, couponCode, totalAmount)
        );

        assertEquals("Coupon code not found" , exception.getMessage());
    }

    @Test
    void shouldThrow_WhenCouponRulesNotMet() {
        Integer userId = 1;
        String couponCode = "SAVE10";
        BigDecimal totalAmount = new BigDecimal("50.00");

        Coupon coupon = createCouponWithRule(couponCode, 10.0, DiscountType.FIXED, RuleType.MINIMUM_AMOUNT, "100");
        CouponUsage couponUsage = new CouponUsage(userId, coupon.getId());

        when(couponRepositoryPort.findByCode(couponCode)).thenReturn(Optional.of(coupon));
        when(couponUsageRepositoryPort.findLastUsageByUserIdAndCouponId(userId, coupon.getId()))
                .thenReturn(Optional.of(couponUsage));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> couponService.applyCoupon(userId, couponCode, totalAmount)
        );

        assertEquals("Coupon conditions are not met.", exception.getMessage());
    }



    @Test
    void shouldApplyCoupon_WithFixedDiscount_GreaterThanTotalAmount_ReturnZero() {
        Integer userId = 1;
        String couponCode = "SAVE200";
        BigDecimal totalAmount = new BigDecimal("100.00");

        Coupon coupon = createCoupon(couponCode, 200.0, DiscountType.FIXED);
        CouponUsage couponUsage = new CouponUsage(userId, coupon.getId());

        when(couponRepositoryPort.findByCode(couponCode)).thenReturn(Optional.of(coupon));
        when(couponUsageRepositoryPort.findLastUsageByUserIdAndCouponId(userId, coupon.getId()))
                .thenReturn(Optional.of(couponUsage));

        AppliedCouponSummary result = couponService.applyCoupon(userId, couponCode, totalAmount);

        assertNotNull(result);
        assertEquals(BigDecimal.ZERO, result.totalAmount());
        assertEquals(totalAmount, result.discountAmount());
    }

    @Test
    void shouldApplyCoupon_WithMinimumAmountRule_WhenAmountSufficient_Successfully() {
        Integer userId = 1;
        String couponCode = "SAVE10";
        BigDecimal totalAmount = new BigDecimal("100.00");

        Coupon coupon = createCouponWithRule(couponCode, 10.0, DiscountType.FIXED, RuleType.MINIMUM_AMOUNT, "50");
        CouponUsage couponUsage = new CouponUsage(userId, coupon.getId());

        when(couponRepositoryPort.findByCode(couponCode)).thenReturn(Optional.of(coupon));
        when(couponUsageRepositoryPort.findLastUsageByUserIdAndCouponId(userId, coupon.getId()))
                .thenReturn(Optional.of(couponUsage));

        AppliedCouponSummary result = couponService.applyCoupon(userId, couponCode, totalAmount);

        assertNotNull(result);
        assertEquals(new BigDecimal("90.00"), result.totalAmount());
    }

    @Test
    void shouldApplyCoupon_WithOncePerUserRule_WhenFirstUse_Successfully() {
        Integer userId = 1;
        String couponCode = "SAVE10";
        BigDecimal totalAmount = new BigDecimal("100.00");

        Coupon coupon = createCouponWithRule(couponCode, 10.0, DiscountType.FIXED, RuleType.ONCE_PER_USER, "true");
        CouponUsage couponUsage = new CouponUsage(userId, coupon.getId());

        when(couponRepositoryPort.findByCode(couponCode)).thenReturn(Optional.of(coupon));
        when(couponUsageRepositoryPort.findLastUsageByUserIdAndCouponId(userId, coupon.getId()))
                .thenReturn(Optional.of(couponUsage));

        AppliedCouponSummary result = couponService.applyCoupon(userId, couponCode, totalAmount);

        assertNotNull(result);
        assertEquals(new BigDecimal("90.00"), result.totalAmount());
    }

    private Coupon createCoupon(String code, Double discount, DiscountType discountType) {
        Coupon coupon = new Coupon();
        coupon.setId(1);
        coupon.setCode(code);
        coupon.setDiscount(discount);
        coupon.setDiscountType(discountType);
        coupon.setStartDate(LocalDate.now().minusDays(1));
        coupon.setEndDate(LocalDate.now().plusDays(30));
        coupon.setRules(List.of());
        return coupon;
    }

    private Coupon createCouponWithRule(String code, Double discount, DiscountType discountType, 
                                        RuleType ruleType, String ruleValue) {
        Coupon coupon = new Coupon();
        coupon.setId(1);
        coupon.setCode(code);
        coupon.setDiscount(discount);
        coupon.setDiscountType(discountType);
        coupon.setStartDate(LocalDate.now().minusDays(1));
        coupon.setEndDate(LocalDate.now().plusDays(30));
        
        CouponRule rule = new CouponRule();
        rule.setType(ruleType);
        rule.setValue(ruleValue);
        coupon.setRules(List.of(rule));
        return coupon;
    }
}