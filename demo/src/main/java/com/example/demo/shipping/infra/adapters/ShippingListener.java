package com.example.demo.shipping.infra.adapters;


import com.example.demo.common.events.OrderPayedEvent;
import com.example.demo.shipping.ports.in.ShippingServicePort;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class ShippingListener {

    private final  ShippingServicePort shippingServicePort;

    public ShippingListener(ShippingServicePort shippingServicePort) {
        this.shippingServicePort = shippingServicePort;
    }


    @ApplicationModuleListener
    public void handle(OrderPayedEvent event) {
       shippingServicePort.shipOrder(event.orderId(), event.orderId());

    }
}
