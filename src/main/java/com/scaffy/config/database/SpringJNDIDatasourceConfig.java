package com.scaffy.config.database;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

@Configuration
public class SpringJNDIDatasourceConfig {
	
	@Value("${database.datasource.jndi}")
	private String jndiDatasource;
	
	@Bean
    public DataSource dataSource() {
		
		 JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
	     
		 dsLookup.setResourceRef(true);
        
        return dsLookup.getDataSource(jndiDatasource);
    }
}
