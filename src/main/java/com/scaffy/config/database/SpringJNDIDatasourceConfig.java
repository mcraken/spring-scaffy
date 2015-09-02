package com.scaffy.config.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
@PropertySource("classpath:database.properties")
public class SpringJNDIDatasourceConfig {
	
	@Value("${database.datasource.jndi}")
	private String jndiDatasource;
	
	@Bean
    public JndiObjectFactoryBean dataSource() {
		
        JndiObjectFactoryBean dataSource = new JndiObjectFactoryBean();
        
        dataSource.setJndiName(jndiDatasource);

        return dataSource;
    }
}
