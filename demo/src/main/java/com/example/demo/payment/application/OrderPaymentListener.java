package com.example.demo.payment.application;

import com.example.demo.common.enums.GeneratedId;
import com.example.demo.common.enums.OrderCancellingCause;
import com.example.demo.common.enums.PaymentType;
import com.example.demo.common.events.OrderCanceledEvent;
import com.example.demo.common.events.OrderPayedEvent;
import com.example.demo.common.events.OrderProductStockVerifiedEvent;
import com.example.demo.common.events.data.OrderPaymentData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderPaymentListener {

private ApplicationEventPublisher publisher;

    public OrderPaymentListener(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @ApplicationModuleListener
    public void handle(OrderProductStockVerifiedEvent event){
        OrderPaymentData paymentInfo = event.getData().paymentInfo();
            if(paymentInfo.type().equals(PaymentType.PAYPAL)) {
                publisher.publishEvent(new OrderCanceledEvent(event.getData(), OrderCancellingCause.PAYMENT_FAILED));
               return;
            }
            // save payment to repo
            event.getData().generatedIds().put(GeneratedId.PAYMENT_ID, UUID.randomUUID());
            publisher.publishEvent(new OrderPayedEvent(event.getData()));
    }

}
