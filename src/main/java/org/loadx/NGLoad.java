package org.loadx;

import org.loadx.application.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableConfigurationProperties
@Import(ApplicationConfig.class)
public class NGLoad {
    public static void main(String[] args) {
        SpringApplication.run(NGLoad.class, args);
    }
}
