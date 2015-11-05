package com.scaffy.config.database;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

@Configuration
public class SpringHibernateConfig {

	@Value("${database.hibernate.config}")
	private String configLocation;
	
	@Bean
	@Autowired
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {

		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

		sessionFactory.setDataSource(dataSource);

		sessionFactory.setConfigLocation(new ClassPathResource(configLocation));

		return sessionFactory;
	}
	
	@Bean
	@Autowired
	public Session entityManager(SessionFactory sessionFactory) {
		 return sessionFactory.getCurrentSession();
	}
}
