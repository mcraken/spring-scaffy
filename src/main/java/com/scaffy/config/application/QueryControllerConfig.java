package com.scaffy.config.application;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Configuration;

import com.scaffy.controller.QueryController;
import com.scaffy.weave.ClassAnnotationWeavelet;
import com.scaffy.weave.RequestMappingBuilder;
import com.scaffy.weave.RestControllerBuilder;

@Configuration
public class QueryControllerConfig extends BeanRegistrarConfig{

	private static Logger logger = LoggerFactory.getLogger(QueryControllerConfig.class);

	public QueryControllerConfig() throws IOException {
	}

	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry registry) throws BeansException {

		try {

			registerBean(
					QueryController.class.getName(), 
					QueryController.class.getName() + "_Bean", 
					new ClassAnnotationWeavelet(
							new RestControllerBuilder(),
							new RequestMappingBuilder(getScaffyProperty("restful.query.uri"))
							),
							registry
					);

		} catch (Exception e) {

			logger.error("Query Controller configurations failed", e);
		} 
	}
}
