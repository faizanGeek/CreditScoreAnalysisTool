package com.ms.credit.config;


import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.HttpAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.JsonLayout;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.net.URL;

/**
 * Configures Log4j2 to send logs to a Splunk server using an HTTP appender.
 */
@Component
public class SplunkConfig {

    /**
     * Initializes logger configuration after the Spring container is fully initialized.
     */
    @PostConstruct
    public void setupLogger() {
        try {
            // Get the current LoggerContext from Log4j2, ensuring it doesn't automatically reload configurations
            final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            final Configuration config = ctx.getConfiguration();

            // Configure JSON layout for log messages
            JsonLayout layout = JsonLayout.newBuilder()
                .setConfiguration(config)
                .setCompact(true)  // Generate compact JSON without unnecessary whitespace
                .setEventEol(true)  // Ensure each event is on a new line
                .build();

            // URL pointing to the Splunk HTTP Event Collector (HEC)
            URL splunkUrl = new URL("http://your-splunk-server:8088/services/collector");

            // Create an HTTP appender to send log messages to Splunk
            HttpAppender appender = HttpAppender.newBuilder()
                .setName("HttpAppender")
                .setUrl(splunkUrl)
                .setLayout(layout)  // Use the JSON layout configured above
                .setConfiguration(config)
                .build();

            // Start the appender and add it to the current logging configuration
            appender.start();
            config.addAppender(appender);
            config.getRootLogger().addAppender(appender, null, null);
            ctx.updateLoggers();  // Apply the new logging configuration to all Log4j2 loggers
        } catch (Exception e) {
            // Handle URL creation or other exceptions
            e.printStackTrace();
        }
    }
}

