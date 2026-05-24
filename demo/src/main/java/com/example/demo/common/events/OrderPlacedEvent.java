package com.example.demo.common.events;


import com.example.demo.common.data.OrderDetails;

public record OrderPlacedEvent(Integer userId, OrderDetails orderDetails) {


}
