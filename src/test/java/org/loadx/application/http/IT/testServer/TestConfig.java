package org.loadx.application.http.IT.testServer;

import org.loadx.application.config.ApplicationConfig;
import org.loadx.application.http.WebsitesHttpConnector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ApplicationConfig.class)
public class TestConfig {

    @Bean
    public WebsitesHttpConnector websitesHttpConnector() {
        return WebsitesHttpConnector.createDefault();
    }

}
