package com.scaffy.config.application;

import java.util.Properties;

import javassist.CannotCompileException;
import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Configuration;

import com.scaffy.controller.MasterRestController;
import com.scaffy.controller.RESTErrorHandler;
import com.scaffy.weave.AnnotationWeavelet;
import com.scaffy.weave.AnnotationWeaver;
import com.scaffy.weave.ClassAnnotationWeavelet;
import com.scaffy.weave.ControllerAdviceBuilder;
import com.scaffy.weave.RequestMappingBuilder;
import com.scaffy.weave.RestControllerBuilder;

@Configuration
public class MasterRestControllerConfig implements BeanDefinitionRegistryPostProcessor{

	private static Logger logger = LoggerFactory.getLogger(MasterRestControllerConfig.class);
	
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {

	}

	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry registry) throws BeansException {
		
		try {
			
			Properties prop = new Properties();
			
			prop.load(getClass().getClassLoader().getResourceAsStream("scaffy.properties"));
			
			registerBean(
					MasterRestController.class.getName(),
					MasterRestController.class.getName() + "_Bean",
					new ClassAnnotationWeavelet(
							new RestControllerBuilder(),
							new RequestMappingBuilder(prop.getProperty("restful.master.uri"))
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
			
			logger.error("Configuring master rest controller Failed!", e);
		} 

	}

	private void registerBean(
			String className, 
			String newClassName, 
			AnnotationWeavelet annotationWeavelet,
			BeanDefinitionRegistry registry)
			throws NotFoundException, CannotCompileException {
		
		AnnotationWeaver annotationWeaver = new AnnotationWeaver(
				className, 
				newClassName, 
				annotationWeavelet
				);

		Class<?> beanClass = annotationWeaver.weave();
		
		RootBeanDefinition bean = new RootBeanDefinition(beanClass, Autowire.BY_TYPE.value(), true);

		registry.registerBeanDefinition(beanClass.getName(), bean);
	}

}
