package com.scaffy.product.interceptor;

import java.lang.annotation.Annotation;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.RootBeanDefinition;

import com.scaffy.product.ProductFactoryLine;
import com.scaffy.product.ProductLine;
import com.scaffy.product.interceptor.InterceptProduct.JoinPoint;
import com.scaffy.weave.AnnotationWeavelet;
import com.scaffy.weave.JoinPointBuilder;
import com.scaffy.weave.MethodAnnotationWeavelet;

@ProductLine(
		productClass = ProductInterceptor.class,
		runtimeAnnotationClass = InterceptProduct.class,
		registerRuntimeBean = true
		)
public class InterceptorProductLine implements ProductFactoryLine {

	public AnnotationWeavelet[] createWeavelets(Annotation targetAnnotation) {

		InterceptProduct interceptProduct = (InterceptProduct) targetAnnotation;

		Class<?> productClass = interceptProduct.productClass();

		Class<?> runtimeClass = interceptProduct.runtimeClass();

		String targetClassName = productClass.getName() + "_" + runtimeClass.getSimpleName();

		String method = interceptProduct.method();

		JoinPoint joinPoint = interceptProduct.join();

		MethodAnnotationWeavelet methodAnnotationWeavelet = null;

		switch(joinPoint) {
		case BEFORE:
			methodAnnotationWeavelet = new MethodAnnotationWeavelet(
					"before", 
					new JoinPointBuilder(Before.class, targetClassName, method)
					);
			break;
		case AFTER: 
			methodAnnotationWeavelet = new MethodAnnotationWeavelet(
					"after", 
					new JoinPointBuilder(After.class, targetClassName, method)
					);
			break;
		case AROUND: 
			methodAnnotationWeavelet = new MethodAnnotationWeavelet(
					"around", 
					new JoinPointBuilder(Around.class, targetClassName, method)
					);
			break;
		}

		return new AnnotationWeavelet[]{methodAnnotationWeavelet};
	}

	public void beforeRegistration(RootBeanDefinition productBean,
			RootBeanDefinition runtimeBean) {

		MutablePropertyValues propertyValues = new MutablePropertyValues();

		propertyValues.add("pointInterceptorClass", runtimeBean.getBeanClass());

		productBean.setPropertyValues(propertyValues);
	}

}
