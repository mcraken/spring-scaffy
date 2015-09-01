package com.scaffy.config.database;

import java.sql.SQLException;

import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:database.properties")
public class OracleDatasource {
	
	private static Logger logger = LoggerFactory.getLogger(OracleDatasource.class);
	
	@Value("${database.user}")
	private String username;
	
	@Value("${database.password}")
	private String password;
	
	@Value("${database.url}")
	private String url;
	
	@Bean
	public DataSource dataSource() throws SQLException{
		
		logger.info("UserName: " + username);
		
		logger.info("URL: " + url);
		
		OracleDataSource dataSource = new OracleDataSource();
		
		dataSource.setUser(username);
		
		dataSource.setPassword(password);
		
		dataSource.setURL(url);
		
		dataSource.setConnectionCachingEnabled(true);
		
		return dataSource;
	}
}
