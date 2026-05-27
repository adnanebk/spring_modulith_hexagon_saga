package com.example.demo.coupon.infra.entities;

import com.example.demo.coupon.domain.DiscountType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "coupon_entity",indexes = {@Index(columnList = "code",name = "isc_code",unique = true)})
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String code;

    private Double discount;
    
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "coupon")
    private List<CouponRuleEntity> rules;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<CouponRuleEntity> getRules() {
        return rules;
    }

    public void setRules(List<CouponRuleEntity> rules) {
        this.rules = rules;
        rules.forEach(rule->rule.setCoupon(this));
    }


}
