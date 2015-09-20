package com.scaffy.config.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:scaffy.properties")
public class ScaffyPropertiesConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {

		return new PropertySourcesPlaceholderConfigurer();
	}
	
}
