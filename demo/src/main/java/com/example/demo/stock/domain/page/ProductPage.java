package com.example.demo.stock.domain.page;

import com.example.demo.stock.domain.Product;

import java.util.List;

public record ProductPage(List<Product> data, int page, int size, int totalElements) {
}
