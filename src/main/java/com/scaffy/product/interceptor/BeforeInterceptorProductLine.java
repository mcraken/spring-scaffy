package com.scaffy.product.interceptor;

import java.lang.annotation.Annotation;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.RootBeanDefinition;

import com.scaffy.product.ProductFactoryLine;
import com.scaffy.product.ProductLine;
import com.scaffy.weave.AnnotationWeavelet;
import com.scaffy.weave.BeforeBuilder;
import com.scaffy.weave.MethodAnnotationWeavelet;

@ProductLine(
		productClass = BeforeProductInterceptor.class,
		runtimeAnnotationClass = BeforeProduct.class,
		registerRuntimeBean = true
		)
public class BeforeInterceptorProductLine implements ProductFactoryLine {

	public AnnotationWeavelet[] createWeavelets(Annotation targetAnnotation) {
		
		BeforeProduct beforeProduct = (BeforeProduct) targetAnnotation;
		
		Class<?> productClass = beforeProduct.productClass();
		
		Class<?> runtimeClass = beforeProduct.runtimeClass();
		
		String targetClassName = productClass.getName() + "_" + runtimeClass.getSimpleName();
		
		String expression = "execution(* "
				+ targetClassName
				+ "."
				+ beforeProduct.method()
				+ "(..))";
		
		return new AnnotationWeavelet[]{
					new MethodAnnotationWeavelet("execute", new BeforeBuilder(expression))
				};
	}

	public void beforeRegistration(RootBeanDefinition productBean,
			RootBeanDefinition runtimeBean) {
		
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		
		propertyValues.add("pointInterceptorClass", runtimeBean.getBeanClass());

		productBean.setPropertyValues(propertyValues);
	}

}
