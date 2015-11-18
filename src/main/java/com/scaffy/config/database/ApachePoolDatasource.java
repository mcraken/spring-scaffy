/**
 * 
 */
package com.scaffy.config.database;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 	Sherief Shawky
 * @Email 	sheshawky@informatique-eg.com
 */
@Configuration
public class ApachePoolDatasource {

	private static final Logger logger = Logger.getLogger(ApachePoolDatasource.class);
	
	@Value("${database.user}")
	private String username;
	
	@Value("${database.password}")
	private String password;
	
	@Value("${database.driver}")
	private String driver;
	
	@Value("${database.url}")
	private String url;
	
	@Bean
	public DataSource dataSource(){
		
		logger.info(
				"Building datasource: " + url
				+ " using UserName: " + username 
				+ ", Password: " + password 
				+", Using driver: " + driver);
		
		BasicDataSource dataSource = new BasicDataSource();
		
		dataSource.setUsername(username);
		
		dataSource.setPassword(password);
		
		dataSource.setDriverClassName(driver);
		
		dataSource.setUrl(url);
		
		return dataSource;
	}
}
