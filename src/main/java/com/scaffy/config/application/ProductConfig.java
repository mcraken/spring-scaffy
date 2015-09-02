package com.scaffy.config.application;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.scaffy.product.ProductFactory;
import com.scaffy.product.ProductFactoryExcpetion;
import com.scaffy.query.jpa.criteriahandlers.CriteriaHandler;
import com.scaffy.query.jpa.criteriahandlers.EqualCriteriaHandler;
import com.scaffy.query.jpa.criteriahandlers.GreaterThanCriterionHandler;
import com.scaffy.query.jpa.criteriahandlers.GreaterThanOrEqualCriterionHandler;
import com.scaffy.query.jpa.criteriahandlers.LessThanCriterionHandler;
import com.scaffy.query.jpa.criteriahandlers.LessThanOrEqualCriterionHandler;
import com.scaffy.query.jpa.criteriahandlers.LikeCriteriaHandler;
import com.scaffy.query.jpa.criteriahandlers.LogicalCriteriaHandler;

@Configuration
public class ProductConfig implements BeanDefinitionRegistryPostProcessor{

	private static Logger logger = LoggerFactory.getLogger(ProductConfig.class);
	
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
			throws BeansException {

	}

	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
			throws BeansException {
		
		try {
			
			ProductFactory productFactory = new ProductFactory(
					"com.scaffy.product",
					"com.test.entities", 
					registry);
			
			productFactory.produce();
			
		} catch (ProductFactoryExcpetion e) {
			
			logger.error("Product Factory Failed!", e);
		}

	}

	@Bean
	public Map<String, CriteriaHandler> criteriaHandlers(){

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
