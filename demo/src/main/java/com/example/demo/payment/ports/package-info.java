@ApplicationModule(displayName = "payment.ports",
        type = ApplicationModule.Type.OPEN,allowedDependencies = {"payment.domain","common"}
)
package com.example.demo.payment.ports;
import org.springframework.modulith.ApplicationModule;