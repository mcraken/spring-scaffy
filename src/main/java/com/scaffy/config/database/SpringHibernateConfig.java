package com.scaffy.config.database;

import java.io.IOException;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringHibernateConfig {

	@Value("${database.hibernate.config}")
	private String configLocation;
	
	@Bean
	@Autowired
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource, PlatformTransactionManager transactinManager) throws IOException {

		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

		sessionFactory.setDataSource(dataSource);

		sessionFactory.setConfigLocation(new ClassPathResource(configLocation));
		
		sessionFactory.setJtaTransactionManager(transactinManager);

		sessionFactory.afterPropertiesSet();
		
		return sessionFactory;
	}
	
	@Bean
	@Scope("prototype")
	@Autowired
	public Session hibernateSession(SessionFactory sessionFactory) {
		return sessionFactory.openSession();
	}
	
}
