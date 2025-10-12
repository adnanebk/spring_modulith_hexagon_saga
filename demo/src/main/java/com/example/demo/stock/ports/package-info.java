@ApplicationModule(displayName = "stock.ports",
        type = ApplicationModule.Type.OPEN,allowedDependencies = {"common","stock.domain"}
)
package com.example.demo.stock.ports;
import org.springframework.modulith.ApplicationModule;