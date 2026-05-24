package com.example.demo.stock.infra.adapters.repo;

import com.example.demo.stock.domain.Product;
import com.example.demo.stock.infra.adapters.mappers.ProductMapper;
import com.example.demo.stock.ports.in.ProductRepoPort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepo implements ProductRepoPort {
    private ProductSpringRepo productSpringRepo;
    private ProductMapper productMapper;

    public ProductRepo(ProductSpringRepo productSpringRepo, ProductMapper productMapper) {
        this.productSpringRepo = productSpringRepo;
        this.productMapper = productMapper;
    }

    @Override
    public List<Product> getAllByIds(List<Integer> ids) {
        return productSpringRepo.findAllById(ids).stream().map(productMapper::toModel).toList();
    }

    @Override
    public void saveAll(List<Product> products) {
        productSpringRepo.saveAllAndFlush(products.stream().map(productMapper::toEntity).toList());
    }

}
