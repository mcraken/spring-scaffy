package com.scaffy.product.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BeforeProduct {
	
	Class<?> productClass();
	
	Class<?> runtimeClass();
	
	String method();
}
