package com.example.demo.stock.infra.adapters.mappers;

import com.example.demo.stock.domain.Product;
import com.example.demo.stock.domain.page.ProductPage;
import com.example.demo.stock.infra.entities.ProductEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
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

    public ProductPage toModel(Page<ProductEntity> page) {
      return new ProductPage(page.getContent().stream().map(this::toModel).toList(), page.getNumber(), page.getSize(), (int)page.getTotalElements());
    }
}
