package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class DemoApplicationTests {

    ApplicationModules modules = ApplicationModules.of(DemoApplication.class);

    @Test
    void createModuleDocumentation() {

        var m = modules.verify();

    }

}
