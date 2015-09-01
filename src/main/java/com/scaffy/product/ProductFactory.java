package com.scaffy.product;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.NotFoundException;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.scaffy.weave.AnnotationWeavelet;
import com.scaffy.weave.AnnotationWeaver;

public class ProductFactory {

	private String target;

	private BeanDefinitionRegistry registry;
	
	public ProductFactory(String target, BeanDefinitionRegistry registry) {

		this.target = target;
		this.registry = registry;
	}

	private <T extends Annotation>Set<BeanDefinition> getTargetBeansDefinitions(Class<T> annotationClass) {

		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);

		provider.addIncludeFilter(new AnnotationTypeFilter(annotationClass));

		Set<BeanDefinition> beans = provider.findCandidateComponents(target);

		return beans;
	}

	private Class<?> weave(ProductFactoryLine<?> productLine, BeanDefinition bean, AnnotationWeavelet... annotationWeavelets) throws NotFoundException, CannotCompileException {

		String className = productLine.getProductClassName();
		
		String beanClassName = bean.getBeanClassName();
		
		AnnotationWeaver annotationWeaver = 
				new AnnotationWeaver(
						className, 
						className + "_" + beanClassName.substring(beanClassName.lastIndexOf(".") + 1),
						annotationWeavelets
						);

		return annotationWeaver.weave();
	}

	private <T extends Annotation>T getTargetAnnotation(BeanDefinition modelBeanDef, Class<T> annotationClass) throws ClassNotFoundException {

		Class<?> modelClass = Class.forName(modelBeanDef.getBeanClassName());

		return modelClass.getAnnotation(annotationClass);
	}

	public <T extends Annotation>void produce(
			Class<T> annotationClass,
			List<ProductFactoryLine<T>> productLines
			) throws ProductFactoryExcpetion {
		
		try {
			
			T targetAnnotation;

			RootBeanDefinition rootDefinition;
			
			Set<BeanDefinition> beans = getTargetBeansDefinitions(annotationClass);
			
			for(BeanDefinition bean : beans) {
				
				targetAnnotation = getTargetAnnotation(bean, annotationClass);
				
				for(ProductFactoryLine<T> productLine : productLines){
					
					Class<?> clazz = weave(
							productLine,
							bean,
							productLine.createWeavelets(targetAnnotation)
							);
					
					rootDefinition = new RootBeanDefinition(clazz, Autowire.BY_TYPE.value(), true);
					
					productLine.beforeRegistration(rootDefinition, bean);
					
					registry.registerBeanDefinition(clazz.getName(), rootDefinition);
				}

			}
			
		} catch (Exception e) {
			
			throw new ProductFactoryExcpetion(e);
		}
	}

}
