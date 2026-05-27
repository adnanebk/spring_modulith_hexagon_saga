package com.example.demo.common.events;


import com.example.demo.common.data.OrderDetails;

public record OrderProductStockVerifiedEvent( OrderDetails orderDetails ) {
}
