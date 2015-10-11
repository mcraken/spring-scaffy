package com.scaffy.config.application;

import java.io.IOException;
import java.util.Properties;

import javassist.CannotCompileException;
import javassist.NotFoundException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

import com.scaffy.weave.AnnotationWeavelet;
import com.scaffy.weave.AnnotationWeaver;

public abstract class BeanRegistrarConfig implements BeanDefinitionRegistryPostProcessor {
	
	private Properties prop;
	
	public BeanRegistrarConfig() throws IOException {
		
		prop = new Properties();
		
		prop.load(getClass().getClassLoader().getResourceAsStream("scaffy.properties"));
	}

	protected String getScaffyProperty(String name) {
		
		return prop.getProperty(name);
	}
	
	protected void registerBean(
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

	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		
	}

}
