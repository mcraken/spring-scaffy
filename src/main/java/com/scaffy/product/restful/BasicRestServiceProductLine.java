package com.scaffy.product.restful;

import java.lang.annotation.Annotation;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

import com.scaffy.product.ProductFactoryLine;
import com.scaffy.product.ProductLine;
import com.scaffy.service.BasicRestService;
import com.scaffy.weave.AnnotationWeavelet;
import com.scaffy.weave.CacheEvictBuilder;
import com.scaffy.weave.CacheableBuilder;
import com.scaffy.weave.MethodAnnotationWeavelet;
import com.scaffy.weave.PreAuthorizeBuilder;
import com.scaffy.weave.TransactionalBuilder;

@ProductLine(
		runtimeAnnotationClass = RestModel.class,
		productClass = BasicRestService.class)
public class BasicRestServiceProductLine implements ProductFactoryLine {

	public AnnotationWeavelet[] createWeavelets(Annotation targetAnnotation) {
		
		RestModel restModelAnnotation = (RestModel) targetAnnotation;
		
		MethodAnnotationWeavelet queryWeavelet = 
				new MethodAnnotationWeavelet(
						"query", 							
						new PreAuthorizeBuilder(restModelAnnotation.authorization()),
						new CacheableBuilder(restModelAnnotation.cacheName())
						);
		
		
		return new AnnotationWeavelet[]{
				queryWeavelet,
				modifierMethodAnnotationWeavlet("save", restModelAnnotation),
				modifierMethodAnnotationWeavlet("update", restModelAnnotation),
				modifierMethodAnnotationWeavlet("delete", restModelAnnotation)
				};
	}

	public void beforeRegistration(RootBeanDefinition productBean, BeanDefinition sourceBean) {
		
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		
		propertyValues.add("modelClass", sourceBean.getBeanClassName());
		
		productBean.setPropertyValues(propertyValues);
	}

	private MethodAnnotationWeavelet modifierMethodAnnotationWeavlet(String methodName, RestModel targetAnnotation) {
		return new MethodAnnotationWeavelet(
				methodName, 							
				new PreAuthorizeBuilder(targetAnnotation.authorization()),
				new CacheEvictBuilder(true),
				new TransactionalBuilder()
				);
	}

}
