package com.scaffy.config.database;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringHibernateConfig {

	@Value("${database.hibernate.config}")
	private String configLocation;
	
	@Bean
	@Autowired
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource, PlatformTransactionManager transactinManager) {

		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

		sessionFactory.setDataSource(dataSource);

		sessionFactory.setConfigLocation(new ClassPathResource(configLocation));
		
		sessionFactory.setJtaTransactionManager(transactinManager);

		return sessionFactory;
	}
	
}
