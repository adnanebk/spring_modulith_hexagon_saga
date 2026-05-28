package com.example.demo.coupon.domain;

import com.example.demo.coupon.domain.validators.CouponRuleValidatorRegistry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

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

        if (this.discountType == DiscountType.FIXED) {
            return totalAmount.subtract(discount).max(BigDecimal.ZERO);
        } else {
            BigDecimal discountAmount = totalAmount.multiply(discount)
                    .divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);
            return totalAmount.subtract(discountAmount);
        }
    }

 public boolean isEligible(Predicate<CouponRule> validator){
     return  rules.stream()
             .allMatch(validator);
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
