package com.example.demo.stock.infra.adapters.repo;

import com.example.demo.stock.domain.Product;
import com.example.demo.stock.domain.SearchProductRequest;
import com.example.demo.stock.domain.page.ProductPage;
import com.example.demo.stock.infra.adapters.mappers.ProductMapper;
import com.example.demo.stock.infra.entities.ProductEntity;
import com.example.demo.stock.ports.out.ProductRepoPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.stock.infra.adapters.repo.SearchProductSpecificationUtils.*;
import static com.example.demo.stock.infra.adapters.repo.SearchProductSpecificationUtils.equalsValue;

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

    @Override
    public ProductPage searchProducts(SearchProductRequest request) {
        Specification<ProductEntity> spec = Specification.allOf(
                includeValue("description", request.searchTerm())
                        .or(equalsValue("name", request.searchTerm())),
                equalsValue("category", request.category()),
                greaterThanOrEqual("price", request.minPrice()),
                lessThanOrEqual("price", request.maxPrice()));
      PageRequest pageRequest = PageRequest.of(request.page(), request.size(), Sort.by(request.sort(), request.direction()));
       return productMapper.toModel(productSpringRepo.findAll(spec,pageRequest));
    }



}
