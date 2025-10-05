package com.example.demo.payment.application;

import com.example.demo.common.enums.OrderCancellingCause;
import com.example.demo.common.enums.PaymentType;
import com.example.demo.common.events.OrderCanceledEvent;
import com.example.demo.common.events.OrderPayedEvent;
import com.example.demo.common.events.OrderProductStockVerifiedEvent;
import com.example.demo.common.models.OrderPayment;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class OrderPaymentListener {

private ApplicationEventPublisher publisher;

    public OrderPaymentListener(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @ApplicationModuleListener
    public void handle(OrderProductStockVerifiedEvent event){
        OrderPayment paymentInfo = event.getOrder().getPaymentInfo();
            if(paymentInfo.getType().equals(PaymentType.PAYPAL)) {
                publisher.publishEvent(new OrderCanceledEvent(event.getOrder(), OrderCancellingCause.PAYMENT_FAILED));
               return;
            }
            paymentInfo.setId(1);
            publisher.publishEvent(new OrderPayedEvent(event.getOrder()));
    }

}
