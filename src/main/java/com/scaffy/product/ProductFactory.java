package com.scaffy.product;

import java.lang.annotation.Annotation;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.scaffy.weave.AnnotationWeavelet;
import com.scaffy.weave.AnnotationWeaver;

public class ProductFactory {

	private static Logger logger = LoggerFactory.getLogger(ProductFactory.class);
	
	private String runtimePackage;

	private BeanDefinitionRegistry registry;

	private String productLinePackage;

	public ProductFactory(
			String productLinePackage,
			String runtimePackage,
			BeanDefinitionRegistry registry) {
		
		this.runtimePackage = runtimePackage;
		this.registry = registry;
		this.productLinePackage = productLinePackage;
	}

	private <T extends Annotation>Set<BeanDefinition> getBeanDefinitionsByAnnotation(Class<T> annotationClass, String targetPackage) {

		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);

		provider.addIncludeFilter(new AnnotationTypeFilter(annotationClass));

		Set<BeanDefinition> beans = provider.findCandidateComponents(targetPackage);

		return beans;
	}

	private Class<?> weave(String className, String beanClassName, AnnotationWeavelet... annotationWeavelets) throws NotFoundException, CannotCompileException {

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

	private void registerProduct(
			Annotation targetAnnotation,
			Class<?> productClass, 
			BeanDefinition runtimeBean,
			BeanDefinition productLineBean) throws NotFoundException,
			CannotCompileException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		logger.info(
				"Weaving " + targetAnnotation.annotationType().getName() +
				", on Product Type " + productClass.getName() +
				", using Product Factory Line: " + productLineBean.getBeanClassName()
				);
		
		ProductFactoryLine productLine = (ProductFactoryLine) Class.forName(productLineBean.getBeanClassName()).newInstance();
		
		Class<?> clazz = weave(
				productClass.getName(),
				runtimeBean.getBeanClassName(),
				productLine.createWeavelets(targetAnnotation)
				);

		RootBeanDefinition rootDefinition = new RootBeanDefinition(clazz, Autowire.BY_TYPE.value(), true);

		productLine.beforeRegistration(rootDefinition, runtimeBean);

		registry.registerBeanDefinition(clazz.getName(), rootDefinition);
	}

	public void produce() throws ProductFactoryExcpetion {
		
		try {
			
			ProductLine productLineAnnotation = null;
			
			Set<BeanDefinition> runtimeBeans = null;
			
			Set<BeanDefinition> productLineBeans = getBeanDefinitionsByAnnotation(ProductLine.class, productLinePackage);
			
			for(BeanDefinition productLineBean : productLineBeans){

				productLineAnnotation = getTargetAnnotation(productLineBean, ProductLine.class);
				
				runtimeBeans = getBeanDefinitionsByAnnotation(productLineAnnotation.runtimeAnnotationClass(), runtimePackage);
				
				for(BeanDefinition runtimeBean : runtimeBeans){
					
					registerProduct(
							getTargetAnnotation(runtimeBean, productLineAnnotation.runtimeAnnotationClass()), 
							productLineAnnotation.productClass(), 
							runtimeBean,
							productLineBean
							);
				}
			}
			
		} catch (Exception e) {
			
			throw new ProductFactoryExcpetion(e);
		}
	}

}
