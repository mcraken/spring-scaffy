package com.scaffy.config.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
public class SpringJPAConfig {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JpaVendorAdapter vendoAdapter;
	
	@Value("${database.jpa.unit}")
	private String persistenceUnit;
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		
		factoryBean.setDataSource(dataSource);
		
		factoryBean.setJpaVendorAdapter(vendoAdapter);
		
		factoryBean.setPersistenceUnitName(persistenceUnit);
		
		return factoryBean;
	}
	
	@Bean
	@Autowired
	@Scope("prototype")
	public EntityManager entityManager(EntityManagerFactory emf) {
		 return emf.createEntityManager();
	}
}
