package com.example.demo.stock.infra.adapters.repo;

import com.example.demo.stock.infra.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductSpringRepo extends JpaRepository<ProductEntity,Integer>, JpaSpecificationExecutor<ProductEntity> {
}
