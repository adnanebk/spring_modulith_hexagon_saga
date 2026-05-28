package com.example.demo.stock.infra.adapters;

import com.example.demo.common.data.OrderDetails;
import com.example.demo.common.events.OrderPaymentFailedEvent;
import com.example.demo.common.events.OrderPlacedEvent;
import com.example.demo.stock.application.StockService;
import com.example.demo.stock.ports.in.StockServicePort;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Component
public class StockListener {

    private final StockServicePort stockService;
    public StockListener(StockService stockService) {
        this.stockService = stockService;
    }

    @ApplicationModuleListener
    public void handle(OrderPlacedEvent event){
        OrderDetails orderDetails = event.orderDetails();
        try {
            stockService.updateProductQuantity(orderDetails);
        } catch (RuntimeException e) {
            stockService.cancelUpdateQuantity(orderDetails.orderId(),e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @ApplicationModuleListener
    public void handle(OrderPaymentFailedEvent event){
        stockService.rollbackProductQuantity(event.orderDetails(),event.message());
    }

}
