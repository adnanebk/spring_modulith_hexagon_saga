package com.example.demo.common.events.data;



import com.example.demo.common.enums.GeneratedId;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record OrderData(Integer id,List<OrderItemData> items, OrderShippingData shipping, OrderPaymentData paymentInfo, Map<GeneratedId, UUID> generatedIds) {
}
