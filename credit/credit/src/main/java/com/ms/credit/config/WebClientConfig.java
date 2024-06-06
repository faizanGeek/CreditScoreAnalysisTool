package com.ms.credit.config;

// Import necessary classes from the Spring Framework for configuration and WebClient.
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClientConfig defines the configuration for the WebClient used in the application.
 * This class leverages Spring's dependency injection to configure and provide a WebClient instance.
 */
@Configuration
public class WebClientConfig {

    /**
     * Creates a WebClient bean using Spring's WebClient.Builder which is automatically configured by Spring.
     * The WebClient instance is built with default settings suitable for most applications but can be customized further if required.
     * @param builder Injected WebClient.Builder which allows for customization of the WebClient.
     * @return A fully configured WebClient instance ready for use in the application.
     */
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();  // Build and return a WebClient instance with default configurations.
    }
}
