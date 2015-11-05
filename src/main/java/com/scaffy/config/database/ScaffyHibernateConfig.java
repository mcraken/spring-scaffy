package com.scaffy.config.database;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.scaffy.acquisition.hibernatequery.criteriahandlers.CriteriaHandler;
import com.scaffy.acquisition.hibernatequery.criteriahandlers.EqualCriteriaHandler;
import com.scaffy.acquisition.hibernatequery.criteriahandlers.GreaterThanCriteriaHandler;
import com.scaffy.acquisition.hibernatequery.criteriahandlers.GreaterThanOrEqualCriteriaHandler;
import com.scaffy.acquisition.hibernatequery.criteriahandlers.LessThanCriteriaHandler;
import com.scaffy.acquisition.hibernatequery.criteriahandlers.LessThanOrEqualCriteriaHandler;
import com.scaffy.acquisition.hibernatequery.criteriahandlers.LikeCriteriaHandler;
import com.scaffy.acquisition.hibernatequery.criteriahandlers.LogicalCriteriaHandler;
import com.scaffy.dao.RESTDao;
import com.scaffy.dao.hibernate.HibernateQueryDao;
import com.scaffy.dao.hibernate.HibernateRESTDao;

@Configuration
public class ScaffyHibernateConfig {
	
	@Bean
	public RESTDao restDao() {
		return new HibernateRESTDao();
	}
	
	@Bean
	public HibernateQueryDao db_hibernate() {
		return new HibernateQueryDao();
	}
	
	@Bean
	public Map<String, CriteriaHandler> hibernateQueryCriteriaHandlers(){

		HashMap<String, CriteriaHandler> criteriaHandlers = new HashMap<String, CriteriaHandler>();

		criteriaHandlers.put("gt", new GreaterThanCriteriaHandler());

		criteriaHandlers.put("eq", new EqualCriteriaHandler());

		criteriaHandlers.put("ge", new GreaterThanOrEqualCriteriaHandler());
		
		criteriaHandlers.put("lt", new LessThanCriteriaHandler());
		
		criteriaHandlers.put("le", new LessThanOrEqualCriteriaHandler());
		
		criteriaHandlers.put("lk", new LikeCriteriaHandler());
		
		criteriaHandlers.put("lg", new LogicalCriteriaHandler());
		
		return criteriaHandlers;
	}
}
