package com.example.demo.stock.infra.repo;

import com.example.demo.stock.infra.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductSpringRepo extends JpaRepository<ProductEntity,Integer> {
}
