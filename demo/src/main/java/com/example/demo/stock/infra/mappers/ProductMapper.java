package com.example.demo.stock.infra.mappers;

import com.example.demo.stock.domain.models.Product;
import com.example.demo.stock.infra.entities.ProductEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toModel(ProductEntity productEntity){
        Product product = new Product();
        BeanUtils.copyProperties(productEntity, product);
        return product;
    }

    public ProductEntity toEntity(Product product){
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(product, productEntity);
        return productEntity;
    }
}
