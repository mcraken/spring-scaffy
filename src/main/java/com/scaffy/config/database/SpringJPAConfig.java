package com.scaffy.config.database;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement(proxyTargetClass = true)
@PropertySource("classpath:database.properties")
public class SpringJPAConfig {
	
	private JpaVendorAdapter vendoAdapter;
	
	@Value("${database.jpa.entities}")
	private String entityPackages;
	
	@Value("${database.jpa.persistence}")
	private String persistenceLocation;
	
	@Bean
	@Autowired
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		
		factoryBean.setDataSource(dataSource);
		
		factoryBean.setJpaVendorAdapter(vendoAdapter);
		
		factoryBean.setPackagesToScan(entityPackages.split(","));
		
		factoryBean.setPersistenceXmlLocation(persistenceLocation);
		
		return factoryBean;
	}
	
	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		
		transactionManager.setEntityManagerFactory(emf);
		
		return transactionManager;
	}
}
