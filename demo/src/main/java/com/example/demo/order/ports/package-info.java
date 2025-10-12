@ApplicationModule(displayName = "order.ports",
        type = ApplicationModule.Type.OPEN,allowedDependencies = {"common","order.domain"}
)
package com.example.demo.order.ports;
import org.springframework.modulith.ApplicationModule;