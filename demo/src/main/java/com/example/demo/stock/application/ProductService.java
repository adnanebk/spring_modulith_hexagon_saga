package com.example.demo.stock.application;


import com.example.demo.stock.domain.SearchProductRequest;
import com.example.demo.stock.domain.page.ProductPage;
import com.example.demo.stock.ports.in.ProductServicePort;
import com.example.demo.stock.ports.out.ProductRepoPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class ProductService implements ProductServicePort {

    private final ProductRepoPort productRepoPort;

    public ProductService(ProductRepoPort productRepoPort) {
        this.productRepoPort = productRepoPort;
    }

    @Override
    public ProductPage searchProducts(SearchProductRequest request) {
        return productRepoPort.searchProducts(request) ;
    }
}
