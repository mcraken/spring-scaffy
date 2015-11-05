package com.scaffy.config.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.WebSphereUowTransactionManager;

@Configuration
public class WebSphereTransactionManager {

	@Bean
	public PlatformTransactionManager transactionManager() {

		WebSphereUowTransactionManager transactionManager = new WebSphereUowTransactionManager();

		transactionManager.setAutodetectTransactionManager(false);

		return transactionManager;
	}
	
}
