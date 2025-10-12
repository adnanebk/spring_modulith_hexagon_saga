package com.example.demo.order.infra.adapters.repo.item;

import com.example.demo.order.infra.entities.ItemEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemSpringRepo extends JpaRepository<ItemEntity, Integer> {

    @Override
    @EntityGraph(attributePaths = {"ingredients"})
    List<ItemEntity> findAllById(Iterable<Integer> integers);
}
