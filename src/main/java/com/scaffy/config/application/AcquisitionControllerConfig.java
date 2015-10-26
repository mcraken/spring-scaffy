package com.scaffy.config.application;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Configuration;

import com.scaffy.controller.AcquisitionController;
import com.scaffy.weave.ClassAnnotationWeavelet;
import com.scaffy.weave.RequestMappingBuilder;
import com.scaffy.weave.RestControllerBuilder;

@Configuration
public class AcquisitionControllerConfig extends BeanRegistrarConfig{

	private static Logger logger = LoggerFactory.getLogger(AcquisitionControllerConfig.class);

	public AcquisitionControllerConfig() throws IOException {
	}

	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry registry) throws BeansException {

		try {

			registerBean(
					AcquisitionController.class.getName(), 
					AcquisitionController.class.getName() + "_Bean", 
					new ClassAnnotationWeavelet(
							new RestControllerBuilder(),
							new RequestMappingBuilder(getScaffyProperty("restful.acquisition.uri"))
							),
							registry
					);
			

		} catch (Exception e) {

			logger.error("Query Controller configurations failed", e);
		} 
	}
}
