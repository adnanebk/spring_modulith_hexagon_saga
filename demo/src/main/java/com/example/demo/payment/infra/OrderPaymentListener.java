package com.example.demo.payment.infra;

import com.example.demo.common.data.OrderDetails;
import com.example.demo.common.events.OrderProductStockVerifiedEvent;
import com.example.demo.payment.application.PaymentService;
import com.example.demo.payment.ports.in.PaymentServicePort;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class OrderPaymentListener {

private final PaymentServicePort paymentServicePort;

    public OrderPaymentListener(PaymentService paymentServicePort) {
        this.paymentServicePort = paymentServicePort;
    }

    @ApplicationModuleListener
    public void handle(OrderProductStockVerifiedEvent event){
        OrderDetails orderDetails = event.orderDetails();
        paymentServicePort.confirmPayment(orderDetails);
    }

}
