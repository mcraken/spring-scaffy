package com.scaffy.config.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class PropertPlaceholderConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {

		return new PropertySourcesPlaceholderConfigurer();
	}
}
