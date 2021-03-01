package com.sahaj.hotel.automation.motion.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AsyncExecutor {

	@Bean
	ExecutorService automationExecutor() {
		return Executors.newFixedThreadPool(5);
	}
	
}
