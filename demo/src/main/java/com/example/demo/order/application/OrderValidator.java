package com.example.demo.order.application;

import com.example.demo.common.data.OrderedItem;
import com.example.demo.common.exceptions.BusinessException;
import com.example.demo.stock.domain.ProductInStock;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class OrderValidator {

    private OrderValidator() {
    }

    public static void validateItems(List<OrderedItem> orderedItems) {
        if (CollectionUtils.isEmpty(orderedItems))
            throw new BusinessException("Order must contain at least one item");
        Set<Integer> uniqueProductIds = new HashSet<>();
        for (OrderedItem item : orderedItems) {
            if (!uniqueProductIds.add(item.productId())) {
                throw new BusinessException("Order must contain unique items");
            }
        }
    }

    public static void validateItemsAvailability(Map<Integer, ProductInStock> productsMap, List<OrderedItem> orderItems) {
        for (OrderedItem item : orderItems) {
            ProductInStock stock = productsMap.get(item.productId());
            if (stock == null || item.quantity() > stock.quantity()) {
                throw new BusinessException("Product " + item.productId() + " is not available in the requested quantity");
            }
        }
    }
}
