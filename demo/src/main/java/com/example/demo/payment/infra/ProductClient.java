package com.example.demo.payment.infra;

import com.example.demo.common.data.StockedProduct;
import com.example.demo.order.ports.out.ProductClientPort;
import com.example.demo.stock.ports.in.StockServicePort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductClient implements ProductClientPort {

    private final StockServicePort stockServicePort;

    public ProductClient(StockServicePort stockServicePort) {
        this.stockServicePort = stockServicePort;
    }

    @Override
    public List<StockedProduct> getProducts(List<Integer> productIds) {
        return stockServicePort.getProducts(productIds);
    }
}
