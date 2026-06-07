package com.example.demo.stock.domain;


import java.math.BigDecimal;

public  class ProductChangeRequest {
    private Integer id;
    private  NullableField<String> name = new NullableField<>();
    private  NullableField<BigDecimal> price=new NullableField<>();
    private  NullableField<String> description = new NullableField<>();
    private  NullableField<Integer> amountInStock = new NullableField<>();

    public ProductChangeRequest(Integer id) {
        this.id = id;
    }



    public NullableField<String> getName() {
        return name;
    }

    public void setName(NullableField<String> name) {
        this.name = name;
    }

    public NullableField<BigDecimal> getPrice() {
        return price;
    }

    public void setPrice(NullableField<BigDecimal> price) {
        this.price = price;
    }

    public NullableField<String> getDescription() {
        return description;
    }

    public void setDescription(NullableField<String> description) {
        this.description = description;
    }

    public NullableField<Integer> getAmountInStock() {
        return amountInStock;
    }

    public void setAmountInStock(NullableField<Integer> amountInStock) {
        this.amountInStock = amountInStock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
