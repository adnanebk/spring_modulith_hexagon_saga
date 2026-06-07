package com.example.demo.stock.ports.in;

import com.example.demo.stock.domain.ProductChangeRequest;
import com.example.demo.stock.domain.SearchProductRequest;
import com.example.demo.stock.domain.page.ProductPage;

public interface ProductServicePort {
    ProductPage searchProducts(SearchProductRequest request);

    void partialUpdate(ProductChangeRequest changeRequest);
}
