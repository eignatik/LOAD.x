package org.loadx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:beans-config.xml")
public class NGLoad {
    public static void main(String[] args) {
        SpringApplication.run(NGLoad.class, args);
    }
}
