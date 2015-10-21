package com.scaffy.config.database;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.scaffy.acquisition.hibernatesearch.criteriahandlers.CriteriaHandler;
import com.scaffy.acquisition.hibernatesearch.criteriahandlers.EqualCriteriaHandler;
import com.scaffy.acquisition.hibernatesearch.criteriahandlers.LogicalCriteriaHandler;
import com.scaffy.acquisition.hibernatesearch.criteriahandlers.NotEqualCriteriaHandler;
import com.scaffy.dao.HibernateSearchDao;
import com.scaffy.dao.SearchDao;

@Configuration
public class ScaffyHibernateSearchConfig {
	
	@Bean
	public SearchDao searchDao() {
		
		return new HibernateSearchDao();
	}
	
	@Bean
	public Map<String, CriteriaHandler> hibernateSearchCriteriaHandlers(){

		HashMap<String, CriteriaHandler> criteriaHandlers = new HashMap<String, CriteriaHandler>();

		criteriaHandlers.put("eq", new EqualCriteriaHandler());
		
		criteriaHandlers.put("ne", new NotEqualCriteriaHandler());

		criteriaHandlers.put("lg", new LogicalCriteriaHandler());
		
		return criteriaHandlers;
	}
}
