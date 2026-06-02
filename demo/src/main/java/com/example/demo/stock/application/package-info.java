@org.springframework.modulith.ApplicationModule(displayName = "stock.application",
        allowedDependencies = {"common","stock.domain","stock.ports","stock::public-api"}
)
package com.example.demo.stock.application;