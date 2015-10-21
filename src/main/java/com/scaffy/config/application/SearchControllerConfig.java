package com.scaffy.config.application;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Configuration;

import com.scaffy.controller.SearchController;
import com.scaffy.weave.ClassAnnotationWeavelet;
import com.scaffy.weave.RequestMappingBuilder;
import com.scaffy.weave.RestControllerBuilder;

@Configuration
public class SearchControllerConfig extends BeanRegistrarConfig{

	private static Logger logger = LoggerFactory.getLogger(SearchControllerConfig.class);

	public SearchControllerConfig() throws IOException {
	}

	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry registry) throws BeansException {

		try {

			registerBean(
					SearchController.class.getName(), 
					SearchController.class.getName() + "_Bean", 
					new ClassAnnotationWeavelet(
							new RestControllerBuilder(),
							new RequestMappingBuilder(getScaffyProperty("restful.search.uri"))
							),
							registry
					);

		} catch (Exception e) {

			logger.error("Query Controller configurations failed", e);
		} 
	}
}
