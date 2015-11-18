package com.scaffy.product;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ProductLine {
	
	Class<? extends Annotation> runtimeAnnotationClass();
	
	Class<?> productClass();
	
	boolean registerRuntimeBean() default false;
}
