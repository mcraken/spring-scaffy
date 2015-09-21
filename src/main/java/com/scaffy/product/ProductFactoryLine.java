package com.scaffy.product;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.support.RootBeanDefinition;

import com.scaffy.weave.AnnotationWeavelet;

public interface ProductFactoryLine {
	
	public abstract AnnotationWeavelet[] createWeavelets(Annotation targetAnnotation);
	
	public abstract void beforeRegistration(RootBeanDefinition productBean, RootBeanDefinition runtimeBean);
	
}
