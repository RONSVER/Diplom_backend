package com.superstore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/** * Этот класс является классом конфигурации для планирования задач с использованием возможностей планирования задач Spring.
 * * Он помечен символом @Configuration, чтобы указать, что он предоставляет определения компонентов.
 * * Он также помечен символом @EnableScheduling, чтобы включить инфраструктуру планирования Spring. */
@Configuration
@EnableScheduling
public class SchedulingConfig {
}
