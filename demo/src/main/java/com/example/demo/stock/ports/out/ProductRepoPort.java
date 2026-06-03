package com.example.demo.stock.ports.out;

import com.example.demo.stock.domain.Product;
import com.example.demo.stock.domain.SearchProductRequest;
import com.example.demo.stock.domain.page.ProductPage;

import java.util.List;

public interface ProductRepoPort {

    List<Product> getAllByIds(List<Integer> ids);

    void saveAll(List<Product> products);

    ProductPage searchProducts(SearchProductRequest request);
}
