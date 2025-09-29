package com.example.demo.stock.infra.repo;

import com.example.demo.stock.infra.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSpringRepo extends JpaRepository<ProductEntity,Integer> {
}
