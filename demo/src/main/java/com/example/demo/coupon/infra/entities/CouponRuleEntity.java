package com.example.demo.coupon.infra.entities;

import com.example.demo.coupon.domain.RuleType;
import jakarta.persistence.*;

@Entity
public class CouponRuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private CouponEntity coupon;
    
    @Enumerated(EnumType.STRING)
    private RuleType type;

    @Column(name = "`value`")
    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public RuleType getType() {
        return type;
    }

    public void setType(RuleType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CouponEntity getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponEntity coupon) {
        this.coupon = coupon;
    }
}
