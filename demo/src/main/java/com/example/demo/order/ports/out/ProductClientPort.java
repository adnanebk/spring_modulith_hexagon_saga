package com.example.demo.order.ports.out;

import com.example.demo.common.data.StockedProduct;

import java.util.List;

public interface ProductClientPort {

    List<StockedProduct> getProductsByIds(List<Integer> productIds);
}
