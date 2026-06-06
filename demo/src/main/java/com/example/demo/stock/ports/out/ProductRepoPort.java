package com.example.demo.stock.ports.out;

import com.example.demo.stock.domain.Product;
import com.example.demo.stock.domain.SearchProductRequest;
import com.example.demo.stock.domain.page.ProductPage;

import java.util.List;
import java.util.Map;

public interface ProductRepoPort {

    List<Product> getAllByIds(List<Integer> ids);

    void updateQuantities(Map<Integer,Integer> productsQuantities);

    ProductPage searchProducts(SearchProductRequest request);
}
