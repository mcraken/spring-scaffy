package com.scaffy.config.database;

import java.io.IOException;
import java.util.Properties;

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
		
		sessionFactory.setHibernateProperties(hibernateProperties());
		
		return sessionFactory;
	}
	
	@Bean
	@Scope("prototype")
	@Autowired
	public Session hibernateSession(SessionFactory sessionFactory) {
		return sessionFactory.openSession();
	}
	
	private Properties hibernateProperties() {

		return new Properties() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				setProperty("hibernate.use_outer_join", "true");
				setProperty("hibernate.bytecode.use_reflection_optimizer", "true");
				setProperty("hibernate.cache.use_second_level_cache", "true");
				setProperty("hibernate.cache.use_query_cache", "true");
				setProperty("hibernate.format_sql", "false");
				setProperty("hibernate.current_session_context_class", "org.springframework.orm.hibernate4.SpringSessionContext");
				setProperty("hibernate.use_outer_join", "org.springframework.orm.hibernate4.SpringSessionContext");
				setProperty("hibernate.transaction.factory_class", "org.hibernate.transaction.CMTTransactionFactory");
				setProperty("hibernate.transaction.manager_lookup_class", "org.hibernate.transaction.WebSphereExtendedJTATransactionLookup");
			}
		};

	}
	
}
