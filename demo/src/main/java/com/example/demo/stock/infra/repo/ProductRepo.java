package com.example.demo.stock.infra.repo;

import com.example.demo.stock.domain.models.Product;
import com.example.demo.stock.domain.ports.in.ProductRepoPort;
import com.example.demo.stock.infra.mappers.ProductMapper;
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
        productSpringRepo.saveAll(products.stream().map(productMapper::toEntity).toList());
        productSpringRepo.flush();
    }

    @Override
    public void increaseStockAmmount() {
        productSpringRepo.findAll().forEach(product-> {
            product.setAmountInStock(product.getAmountInStock() + 10);
        });
    }
}
