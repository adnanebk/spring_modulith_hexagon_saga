package com.example.demo.stock.infra.adapters;

import com.example.demo.common.data.OrderDetails;
import com.example.demo.common.events.OrderPaymentFailedEvent;
import com.example.demo.common.events.OrderPlacedEvent;
import com.example.demo.stock.application.StockService;
import com.example.demo.stock.ports.out.StockServicePort;
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
        OrderDetails orderDetails = event.orderDetails();
            stockService.updateProductQuantity(orderDetails);
    }


    @ApplicationModuleListener
    public void handle(OrderPaymentFailedEvent event){
        stockService.rollbackProductQuantity(event.orderDetails(),event.message());
    }

}
