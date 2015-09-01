package com.scaffy.product;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

import com.scaffy.weave.AnnotationWeavelet;

public interface ProductFactoryLine<T> {
	
	public abstract AnnotationWeavelet[] createWeavelets(T targetAnnotation);
	
	public abstract void beforeRegistration(RootBeanDefinition beanDefinition, BeanDefinition sourceBean);
	
	public String getProductClassName();
}
