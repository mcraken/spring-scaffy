package com.scaffy.product.restful;

import java.lang.annotation.Annotation;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.RootBeanDefinition;

import com.scaffy.product.ProductFactoryLine;
import com.scaffy.product.ProductLine;
import com.scaffy.service.BasicSearchService;
import com.scaffy.weave.AnnotationWeavelet;
import com.scaffy.weave.CacheableBuilder;
import com.scaffy.weave.MethodAnnotationWeavelet;
import com.scaffy.weave.PreAuthorizeBuilder;

@ProductLine(
		runtimeAnnotationClass = Searchable.class,
		productClass = BasicSearchService.class)
public class BasicSearchServiceProductLine implements ProductFactoryLine {

	public AnnotationWeavelet[] createWeavelets(Annotation targetAnnotation) {
		
		Searchable queryableAnnotation = (Searchable) targetAnnotation;
		
		return new AnnotationWeavelet[]{
				new MethodAnnotationWeavelet(
						"acquire", 							
						new PreAuthorizeBuilder(queryableAnnotation.authorization()),
						new CacheableBuilder(queryableAnnotation.cacheName())
						)
				};
		
	}

	public void beforeRegistration(RootBeanDefinition productBean, RootBeanDefinition sourceBean) {
		
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		
		propertyValues.add("modelClass", sourceBean.getBeanClassName());

		productBean.setPropertyValues(propertyValues);
	}

}
