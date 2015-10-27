package com.scaffy.product.restful;

import java.lang.annotation.Annotation;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.RootBeanDefinition;

import com.scaffy.product.ProductFactoryLine;
import com.scaffy.product.ProductLine;
import com.scaffy.product.restful.Searchable.DBSearchType;
import com.scaffy.product.restful.Searchable.FTSearchType;
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

		Searchable searchableAnnotation = (Searchable) targetAnnotation;

		return new AnnotationWeavelet[]{
				new MethodAnnotationWeavelet(
						"db", 							
						new PreAuthorizeBuilder(searchableAnnotation.authorization()),
						new CacheableBuilder(searchableAnnotation.cacheName())
						),
				new MethodAnnotationWeavelet(
						"fullText", 							
						new PreAuthorizeBuilder(searchableAnnotation.authorization()),
						new CacheableBuilder(searchableAnnotation.cacheName())
						)
		};

	}

	public void beforeRegistration(Annotation targetAnnotation, RootBeanDefinition productBean, RootBeanDefinition sourceBean) {

		MutablePropertyValues propertyValues = new MutablePropertyValues();

		propertyValues.add("modelClass", sourceBean.getBeanClassName());

		Searchable searchableAnnotation = (Searchable) targetAnnotation;

		if(searchableAnnotation.dbType() != DBSearchType.IGNORE)
			propertyValues.add("dbSearchType", searchableAnnotation.dbType().value);

		if(searchableAnnotation.ftType() != FTSearchType.IGNORE)
			propertyValues.add("ftSearchType", searchableAnnotation.ftType().value);

		productBean.setPropertyValues(propertyValues);
	}

}
