package com.example.hw3.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    // Sử dụng ConcurrentMap hoặc Redis default, @EnableCaching là đủ để kích hoạt AOP Proxy.
}
