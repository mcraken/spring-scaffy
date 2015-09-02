package com.scaffy.config.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;

import com.scaffy.product.ProductFactory;
import com.scaffy.product.ProductFactoryExcpetion;

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

}
