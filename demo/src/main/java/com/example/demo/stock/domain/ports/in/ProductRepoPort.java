package com.example.demo.stock.domain.ports.in;

import com.example.demo.stock.domain.models.Product;

import java.util.List;

public interface ProductRepoPort {

    List<Product> getAllByIds(List<Integer> ids);

    void saveAll(List<Product> products);

    void increaseStockAmmount();
}
