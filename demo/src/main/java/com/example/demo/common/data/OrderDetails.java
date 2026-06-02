package com.example.demo.common.data;

import java.util.List;

public record OrderDetails(Integer orderId, Integer userId, String paymentToken, List<OrderedItem> items) {

}
