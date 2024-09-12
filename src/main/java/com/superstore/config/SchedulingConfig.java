package com.superstore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * This class is a configuration class for scheduling tasks using Spring's Task Scheduling capabilities.
 * It is annotated with @Configuration to indicate that it provides bean definitions.
 * It is also annotated with @EnableScheduling to enable Spring's scheduling infrastructure.
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {
}
