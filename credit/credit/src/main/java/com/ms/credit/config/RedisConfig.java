package com.ms.credit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Configuration class for Redis.
 * Configures a RedisTemplate to interact with Redis data store using custom serialization strategies.
 */
@Configuration
public class RedisConfig {

    /**
     * Configures a RedisTemplate for operations on Redis.
     * RedisTemplate is configured with serializers for both keys and values to ensure proper data format is maintained.
     * @param connectionFactory the RedisConnectionFactory to manage connections to the Redis server.
     * @return a fully configured RedisTemplate.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);  // Set the connection factory that manages Redis connections.
        template.setKeySerializer(new StringRedisSerializer());  // Set the key serializer to serialize string keys.
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());  // Set the value serializer to serialize values as JSON.
        return template;  // Return the configured RedisTemplate.
    }
}
