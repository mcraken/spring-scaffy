package com.scaffy.config.application;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.scaffy.product.ProductFactory;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ProductConfig implements BeanDefinitionRegistryPostProcessor{

	private static Logger logger = LoggerFactory.getLogger(ProductConfig.class);
	
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
			throws BeansException {

	}

	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
			throws BeansException {
		
		try {
			
			Properties prop = new Properties();
			
			prop.load(getClass().getClassLoader().getResourceAsStream("scaffy.properties"));
			
			ProductFactory productFactory = new ProductFactory(
					prop.getProperty("scaffy.products.productPackages").split(","),
					prop.getProperty("scaffy.products.runtimePackages").split(","), 
					registry);
			
			productFactory.produce();
			
		} catch (Exception e) {
			
			logger.error("Product Factory Failed!", e);
		}

	}
	
}
