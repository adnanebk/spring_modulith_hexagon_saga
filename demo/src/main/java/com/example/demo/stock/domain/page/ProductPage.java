package com.example.demo.stock.domain.page;

import com.example.demo.stock.domain.Product;

import java.util.List;

public record ProductPage(List<Product> products, int page, int size, int total) {
}
