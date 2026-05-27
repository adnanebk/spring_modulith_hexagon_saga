package com.example.demo.order.infra.adapters.repo.item;

import com.example.demo.order.infra.entities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemSpringRepo extends JpaRepository<ItemEntity, Integer> {


}
