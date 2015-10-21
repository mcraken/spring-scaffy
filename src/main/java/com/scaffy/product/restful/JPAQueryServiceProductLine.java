package com.scaffy.product.restful;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.support.RootBeanDefinition;

import com.scaffy.product.ProductFactoryLine;
import com.scaffy.product.ProductLine;
import com.scaffy.service.JPAQueryService;
import com.scaffy.weave.AnnotationWeavelet;
import com.scaffy.weave.CacheableBuilder;
import com.scaffy.weave.MethodAnnotationWeavelet;
import com.scaffy.weave.PreAuthorizeBuilder;

@ProductLine(
		runtimeAnnotationClass = Queryable.class,
		productClass = JPAQueryService.class)
public class JPAQueryServiceProductLine implements ProductFactoryLine {

	public AnnotationWeavelet[] createWeavelets(Annotation targetAnnotation) {
		
		Queryable queryableAnnotation = (Queryable) targetAnnotation;
		
		return new AnnotationWeavelet[]{
				new MethodAnnotationWeavelet(
						"query", 							
						new PreAuthorizeBuilder(queryableAnnotation.authorization()),
						new CacheableBuilder(queryableAnnotation.cacheName())
						)
				};
		
	}

	public void beforeRegistration(RootBeanDefinition productBean, RootBeanDefinition sourceBean) {
		
	}

}
