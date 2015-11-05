package com.scaffy.config.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.scaffy.dao.SearchDao;
import com.scaffy.dao.hibernate.JPAHibernateSearchDao;

@Configuration
public class ScaffyJPASearchConfig {
	
	@Bean
	public SearchDao ft_hibernate_jpa() {
		
		return new JPAHibernateSearchDao();
	}

}
