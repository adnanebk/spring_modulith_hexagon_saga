package com.example.demo.stock.infra.adapters;

import com.example.demo.common.data.OrderDetails;
import com.example.demo.common.events.OrderPaymentFailedEvent;
import com.example.demo.common.events.OrderPlacedEvent;
import com.example.demo.common.events.OrderStockFailedEvent;
import com.example.demo.stock.application.StockService;
import com.example.demo.stock.ports.out.StockServicePort;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
@Retryable(
        retryFor = { ObjectOptimisticLockingFailureException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 500, multiplier = 2.0)
)
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
    @Recover
    public void recover(Exception e,OrderPaymentFailedEvent event) {
        stockService.cancelProductReservation(e, event.orderDetails());
    }
    @Recover
    public void recover(Exception e,OrderPlacedEvent event) {
        stockService.cancelProductReservation(e, event.orderDetails());
    }

}
