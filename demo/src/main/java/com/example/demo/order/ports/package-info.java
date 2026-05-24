@ApplicationModule(displayName = "order.ports",
        type = ApplicationModule.Type.OPEN,allowedDependencies = {"order.domain","common"}
)
package com.example.demo.order.ports;
import org.springframework.modulith.ApplicationModule;