package com.ms.credit.config;

// Import necessary classes from Spring Framework and Kafka libraries.
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.ms.credit.dto.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.HashMap;
import java.util.Map;

/**
 * KafkaProducerConfig sets up the Kafka producer configuration for the application.
 * This configuration includes defining beans for the producer factory and KafkaTemplate.
 */
@Configuration
public class KafkaProducerConfig {
    
    /**
     * Creates a ProducerFactory bean that configures the Kafka producer.
     * This factory is responsible for creating Kafka producer instances with specified serializers for keys and values.
     * @return ProducerFactory for Kafka producers that can send NotificationDTO objects.
     */
    @Bean
    public ProducerFactory<String, NotificationDTO> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");  // Kafka server address.
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);  // Serializer for message keys.
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);  // Serializer for message values.
        return new DefaultKafkaProducerFactory<>(configProps);  // Return the producer factory configured with these settings.
    }

    /**
     * Configures and returns a KafkaTemplate.
     * KafkaTemplate simplifies sending messages to Kafka topics by providing higher-level operations.
     * It is configured here to use the producer factory that produces NotificationDTO messages.
     * @return KafkaTemplate to send NotificationDTO messages to Kafka.
     */
    @Bean
    public KafkaTemplate<String, NotificationDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());  // Create and return a KafkaTemplate based on the producer factory.
    }
}
