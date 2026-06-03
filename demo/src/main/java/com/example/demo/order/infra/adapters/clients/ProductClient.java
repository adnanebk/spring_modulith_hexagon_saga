package com.example.demo.order.infra.adapters.clients;

import com.example.demo.order.ports.out.ProductClientPort;
import com.example.demo.stock.domain.ProductInStock;
import com.example.demo.stock.ports.out.StockServicePort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductClient implements ProductClientPort {

    private final StockServicePort stockServicePort;

    public ProductClient(StockServicePort stockServicePort) {
        this.stockServicePort = stockServicePort;
    }

    @Override
    public List<ProductInStock> getProductsByIds(List<Integer> productIds) {
        return stockServicePort.getProducts(productIds);
    }
}
