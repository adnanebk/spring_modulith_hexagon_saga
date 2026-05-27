@ApplicationModule(displayName = "shipping.ports",
        type = ApplicationModule.Type.OPEN,allowedDependencies = {"shipping.domain","common"}
)
package com.example.demo.shipping.ports;
import org.springframework.modulith.ApplicationModule;