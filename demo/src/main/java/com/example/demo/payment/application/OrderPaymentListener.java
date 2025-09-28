package com.example.demo.payment.application;

import com.example.demo.common.enums.OrderCancellingCause;
import com.example.demo.common.enums.PaymentType;
import com.example.demo.common.events.OrderCanceledEvent;
import com.example.demo.common.events.OrderPayedEvent;
import com.example.demo.common.events.OrderProductStockVerifiedEvent;
import com.example.demo.common.eventsdata.OrderEventData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderPaymentListener {

private ApplicationEventPublisher publisher;

    public OrderPaymentListener(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @EventListener
    public void handle(OrderProductStockVerifiedEvent event){
       OrderEventData paymentInfo = event.getOrder();
            if(paymentInfo.getPaymentType().equals(PaymentType.PAYPAL)) {
                publisher.publishEvent(new OrderCanceledEvent(event.getOrder(), OrderCancellingCause.PAYMENT_FAILED));
                return;
            }
            paymentInfo.setPaymentId(1);
            publisher.publishEvent(new OrderPayedEvent(event.getOrder()));
    }

}
