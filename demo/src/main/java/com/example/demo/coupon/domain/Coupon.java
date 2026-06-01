package com.example.demo.coupon.domain;


import com.example.demo.coupon.domain.validators.CouponRuleValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Coupon {
    private Integer id;
    private String code;
    private Double discount;
    private DiscountType discountType;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<CouponRule> rules;



    public BigDecimal calculateFinalAmount(BigDecimal totalAmount) {
        BigDecimal discount = BigDecimal.valueOf(this.discount);
        return discountType.makeDiscount(totalAmount, discount);
    }

 public boolean isEligible(List<CouponUsage> usageHistory, BigDecimal totalAmount, CouponRuleValidator couponValidator){
     ApplyCouponRequest request =
             new ApplyCouponRequest(this, usageHistory, totalAmount);
        return  rules.stream()
                .allMatch(rule -> couponValidator.validate(rule,request));
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<CouponRule> getRules() {
        return rules;
    }

    public void setRules(List<CouponRule> rules) {
        this.rules = rules;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
