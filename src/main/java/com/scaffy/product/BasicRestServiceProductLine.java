package com.scaffy.product;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

import com.scaffy.weave.AnnotationWeavelet;
import com.scaffy.weave.CacheEvictBuilder;
import com.scaffy.weave.CacheableBuilder;
import com.scaffy.weave.MethodAnnotationWeavelet;
import com.scaffy.weave.PreAuthorizeBuilder;
import com.scaffy.weave.TransactionalBuilder;

public class BasicRestServiceProductLine implements ProductFactoryLine<RestModel> {

	public AnnotationWeavelet[] createWeavelets(RestModel targetAnnotation) {
		
		MethodAnnotationWeavelet queryWeavelet = 
				new MethodAnnotationWeavelet(
						"query", 							
						new PreAuthorizeBuilder(targetAnnotation.authorization()),
						new CacheableBuilder(targetAnnotation.cacheName())
						);
		
		
		return new AnnotationWeavelet[]{
				queryWeavelet,
				modifierMethodAnnotationWeavlet("save", targetAnnotation),
				modifierMethodAnnotationWeavlet("update", targetAnnotation),
				modifierMethodAnnotationWeavlet("delete", targetAnnotation)
				};
	}

	public void beforeRegistration(RootBeanDefinition productBean, BeanDefinition sourceBean) {
		
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		
		propertyValues.add("modelClass", sourceBean.getBeanClassName());
		
		productBean.setPropertyValues(propertyValues);
	}

	public String getProductClassName() {
		return "com.test.service.BasicRestService";
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
