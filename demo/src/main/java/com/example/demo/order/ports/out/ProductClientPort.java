package com.example.demo.order.ports.out;

import com.example.demo.common.data.ProductInStock;

import java.util.List;

public interface ProductClientPort {

    List<ProductInStock> getProductsByIds(List<Integer> productIds);
}
