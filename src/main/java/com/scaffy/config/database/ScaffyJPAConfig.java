package com.scaffy.config.database;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.scaffy.acquisition.jpa.criteriahandlers.CriteriaHandler;
import com.scaffy.acquisition.jpa.criteriahandlers.EqualCriteriaHandler;
import com.scaffy.acquisition.jpa.criteriahandlers.GreaterThanCriterionHandler;
import com.scaffy.acquisition.jpa.criteriahandlers.GreaterThanOrEqualCriterionHandler;
import com.scaffy.acquisition.jpa.criteriahandlers.LessThanCriterionHandler;
import com.scaffy.acquisition.jpa.criteriahandlers.LessThanOrEqualCriterionHandler;
import com.scaffy.acquisition.jpa.criteriahandlers.LikeCriteriaHandler;
import com.scaffy.acquisition.jpa.criteriahandlers.LogicalCriteriaHandler;
import com.scaffy.dao.JPARESTDao;
import com.scaffy.dao.JPASearchDao;
import com.scaffy.dao.RESTDao;

@Configuration
public class ScaffyJPAConfig {
	
	@Bean
	public RESTDao restDao() {
		return new JPARESTDao();
	}
	
	public JPASearchDao db_jpa() {
		return new JPASearchDao();
	}
	
	@Bean
	public Map<String, CriteriaHandler> jpaCriteriaHandlers(){

		HashMap<String, CriteriaHandler> criteriaHandlers = new HashMap<String, CriteriaHandler>();

		criteriaHandlers.put("gt", new GreaterThanCriterionHandler());

		criteriaHandlers.put("eq", new EqualCriteriaHandler());

		criteriaHandlers.put("ge", new GreaterThanOrEqualCriterionHandler());
		
		criteriaHandlers.put("lt", new LessThanCriterionHandler());
		
		criteriaHandlers.put("le", new LessThanOrEqualCriterionHandler());
		
		criteriaHandlers.put("lk", new LikeCriteriaHandler());
		
		criteriaHandlers.put("lg", new LogicalCriteriaHandler());
		
		return criteriaHandlers;
	}
}
