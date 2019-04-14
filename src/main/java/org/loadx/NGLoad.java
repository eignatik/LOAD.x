package org.loadx;

import org.loadx.application.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@Import(ApplicationConfig.class)
public class NGLoad {
    public static void main(String[] args) {
        SpringApplication.run(NGLoad.class, args);
    }
}
