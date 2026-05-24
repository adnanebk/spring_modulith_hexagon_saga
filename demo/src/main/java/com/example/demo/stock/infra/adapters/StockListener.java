package com.example.demo.stock.infra.adapters;

import com.example.demo.common.events.OrderPaymentFailedEvent;
import com.example.demo.common.events.OrderPlacedEvent;
import com.example.demo.stock.application.StockService;
import com.example.demo.stock.ports.in.StockServicePort;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class StockListener {

    private final StockServicePort stockService;
    public StockListener(StockService stockService) {
        this.stockService = stockService;
    }

    @ApplicationModuleListener
    public void handle(OrderPlacedEvent event){
        try {
            stockService.updateProductQuantity(event.orderDetails());
        } catch (RuntimeException e) {
            stockService.cancelUpdateQuantity(event.orderDetails().orderId(),e.getMessage());
        }
    }

    @ApplicationModuleListener
    public void handle(OrderPaymentFailedEvent event){
        stockService.rollbackProductQuantity(event.orderDetails(),event.message());
    }

}
