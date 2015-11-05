package com.scaffy.config.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.scaffy.dao.SearchDao;
import com.scaffy.dao.hibernate.SessionHibernateSearchDao;

@Configuration
public class ScaffySessionSearchConfig {
	
	@Bean
	public SearchDao ft_hibernate_session() {
		
		return new SessionHibernateSearchDao();
	}

}
