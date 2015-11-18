package com.scaffy.config.application;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Configuration;

import com.scaffy.controller.MasterRestController;
import com.scaffy.controller.RESTErrorHandler;
import com.scaffy.controller.SearchController;
import com.scaffy.service.ModelUnmarshaller;
import com.scaffy.service.ServiceBroker;
import com.scaffy.weave.ClassAnnotationWeavelet;
import com.scaffy.weave.ControllerAdviceBuilder;
import com.scaffy.weave.RequestMappingBuilder;
import com.scaffy.weave.RestControllerBuilder;

@Configuration
public class ScaffyApplicationConfig extends BeanRegistrarConfig{

	private static Logger logger = LoggerFactory.getLogger(ScaffyApplicationConfig.class);

	public ScaffyApplicationConfig() throws IOException {

	}

	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry registry) throws BeansException {

		try {

			registerBean(ServiceBroker.class, registry);
			
			registerBean(ModelUnmarshaller.class, registry);
			
			registerBean(
					MasterRestController.class.getName(),
					MasterRestController.class.getName() + "_Bean",
					new ClassAnnotationWeavelet(
							new RestControllerBuilder(),
							new RequestMappingBuilder(getScaffyProperty("restful.master.uri"))
							),
							registry
					);

			registerBean(
					SearchController.class.getName(), 
					SearchController.class.getName() + "_Bean", 
					new ClassAnnotationWeavelet(
							new RestControllerBuilder(),
							new RequestMappingBuilder(getScaffyProperty("restful.search.uri"))
							),
							registry
					);

			registerBean(
					RESTErrorHandler.class.getName(),
					RESTErrorHandler.class.getName() + "_Bean",
					new ClassAnnotationWeavelet(
							new ControllerAdviceBuilder()
							),
							registry
					);

		} catch (Exception e) {

			logger.error("Configuring scaffy bean has Failed!", e);
		} 

	}

}
