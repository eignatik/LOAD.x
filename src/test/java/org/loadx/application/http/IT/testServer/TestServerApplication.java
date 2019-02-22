package org.loadx.application.http.IT.testServer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class TestServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestServerApplication.class, args);
    }
}
