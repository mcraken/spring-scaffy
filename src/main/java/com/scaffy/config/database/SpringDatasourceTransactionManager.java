package com.scaffy.config.database;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement(proxyTargetClass = true)
public class SpringDatasourceTransactionManager {
	
	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(DataSource datasource) {
		
		return new DataSourceTransactionManager(datasource);
	}
}
