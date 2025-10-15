package com.example.demo.stock.application;

import com.example.demo.common.enums.GeneratedId;
import com.example.demo.common.enums.OrderCancellingCause;
import com.example.demo.common.events.OrderCanceledEvent;
import com.example.demo.common.events.OrderPlacedEvent;
import com.example.demo.common.events.OrderProductStockVerifiedEvent;
import com.example.demo.stock.domain.exeptions.NotEnoughStockException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
            stockEventService.updateProductQuantity(event.getData().items());
            event.getData().generatedIds().put(GeneratedId.STOCK_ID, UUID.randomUUID());
            publisher.publishEvent(new OrderProductStockVerifiedEvent(event.getData()));
        } catch (NotEnoughStockException e) {
            publisher.publishEvent(new OrderCanceledEvent(event.getData(), OrderCancellingCause.NOT_ENOUGH_STOCK));
        }
    }
    @ApplicationModuleListener
    public void handle(OrderCanceledEvent event){
           if(event.getData().generatedIds().containsKey(GeneratedId.STOCK_ID))
             stockEventService.rollbackProductQuantity(event.getData().items());
    }

}
