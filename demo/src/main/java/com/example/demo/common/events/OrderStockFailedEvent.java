package com.example.demo.common.events;



public record OrderStockFailedEvent(Integer orderId,String message) {
}
