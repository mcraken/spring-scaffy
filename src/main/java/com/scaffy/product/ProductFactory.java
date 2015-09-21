package com.scaffy.product;

import java.lang.annotation.Annotation;
import java.util.HashSet;
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
	
	private String[] runtimePackages;

	private BeanDefinitionRegistry registry;

	private String[] productLinePackages;

	public ProductFactory(
			String[] productLinePackages,
			String[] runtimePackages,
			BeanDefinitionRegistry registry) {
		
		this.runtimePackages = runtimePackages;
		this.registry = registry;
		this.productLinePackages = productLinePackages;
	}

	private <T extends Annotation>Set<BeanDefinition> getBeanDefinitionsByAnnotation(Class<T> annotationClass, String[] targetPackages) throws NoBeansFoundException {

		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);

		provider.addIncludeFilter(new AnnotationTypeFilter(annotationClass));
		
		Set<BeanDefinition> allBeans = new HashSet<BeanDefinition>(); 
		
		Set<BeanDefinition> beans = null;
		
		for(String targetPackage : targetPackages) {
			
			beans = provider.findCandidateComponents(targetPackage);
			
			if(beans != null && beans.size() != 0)
				allBeans.addAll(beans);
		}
		
		if(allBeans.size() == 0){
			
			String packages = "No beans found under: ";
			
			for(String targetPackage : targetPackages)
				packages += targetPackage + " ";
			
			throw new NoBeansFoundException(packages);
		}
		
		return allBeans;
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
			ProductLine productLineAnnotation,
			BeanDefinition runtimeBean,
			BeanDefinition productLineBean) throws NotFoundException,
			CannotCompileException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		Annotation targetAnnotation = getTargetAnnotation(runtimeBean, productLineAnnotation.runtimeAnnotationClass());
		
		Class<?> productClass = productLineAnnotation.productClass();
		
		logger.info(
				"Weaving " + targetAnnotation.annotationType().getName() +
				", on Product Type " + productClass.getName() +
				", using Product Factory Line: " + productLineBean.getBeanClassName()
				);
		
		ProductFactoryLine productLine = (ProductFactoryLine) Class.forName(productLineBean.getBeanClassName()).newInstance();
		
		Class<?> annotatedProductClass = weave(
				productClass.getName(),
				runtimeBean.getBeanClassName(),
				productLine.createWeavelets(targetAnnotation)
				);

		Class<?> runtimeBeanClass = Class.forName(runtimeBean.getBeanClassName());
		
		RootBeanDefinition productBean = new RootBeanDefinition(annotatedProductClass, Autowire.BY_TYPE.value(), true);
		
		RootBeanDefinition runtimeBeanDef = new RootBeanDefinition(runtimeBeanClass, Autowire.BY_TYPE.value(), true);
		
		productLine.beforeRegistration(productBean, runtimeBeanDef);

		registry.registerBeanDefinition(annotatedProductClass.getName(), productBean);
		
		if(productLineAnnotation.registerRuntimeBean())
			registry.registerBeanDefinition(runtimeBeanClass.getName(), runtimeBeanDef);
	}

	public void produce() throws ProductFactoryExcpetion {
		
		try {
			
			ProductLine productLineAnnotation = null;
			
			Set<BeanDefinition> runtimeBeans = null;
			
			Set<BeanDefinition> productLineBeans = getBeanDefinitionsByAnnotation(ProductLine.class, productLinePackages);
			
			for(BeanDefinition productLineBean : productLineBeans){

				productLineAnnotation = getTargetAnnotation(productLineBean, ProductLine.class);
				
				runtimeBeans = getBeanDefinitionsByAnnotation(productLineAnnotation.runtimeAnnotationClass(), runtimePackages);
				
				for(BeanDefinition runtimeBean : runtimeBeans){
					
					registerProduct(
							productLineAnnotation, 
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
