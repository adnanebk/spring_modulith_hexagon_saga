package com.example.demo.stock.application;

import com.example.demo.common.enums.OrderCancellingCause;
import com.example.demo.common.events.OrderCanceledEvent;
import com.example.demo.common.events.OrderPlacedEvent;
import com.example.demo.common.events.OrderProductStockVerifiedEvent;
import com.example.demo.stock.domain.exeptions.NotEnoughStockException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class StockListener {

    private StockEventService stockEventService;
    private ApplicationEventPublisher publisher;

    public StockListener(StockEventService stockEventService, ApplicationEventPublisher publisher) {
        this.stockEventService = stockEventService;
        this.publisher = publisher;
    }

    @ApplicationModuleListener
    public void handle(OrderPlacedEvent event){
        try {
            stockEventService.updateStock(event.getOrder().getItems());
            publisher.publishEvent(new OrderProductStockVerifiedEvent(event.getOrder()));
        } catch (NotEnoughStockException e) {
            publisher.publishEvent(new OrderCanceledEvent(event.getOrder(), OrderCancellingCause.NOT_ENOUGH_STOCK));
        }
    }
    @ApplicationModuleListener
    public void handle(OrderCanceledEvent event){
           if(event.getOrderCancellingCause().equals(OrderCancellingCause.PAYMENT_FAILED))
            stockEventService.rollbackStock(event.getOrder().getItems());
    }

}
