package com.scaffy.config.database;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class SpringEmbeddedDatasource {

	@Value("${database.embedded.type}")
	private String embeddedDatabaseType;
	
	@Value("${database.embedded.scripts}")
	private String embeddedScripts;
	
	@Bean(destroyMethod = "shutdown")
	public DataSource dataSource() {
		
		EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
		
		databaseBuilder.setType(EmbeddedDatabaseType.valueOf(embeddedDatabaseType));
		
		databaseBuilder.addScripts(embeddedScripts.split(","));
		
		return databaseBuilder.build();
	}
}
