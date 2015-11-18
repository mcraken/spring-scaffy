package com.scaffy.product.restful;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RestModel {
	
	boolean secure() default false;
	
	String authorization() default "";
	
	boolean cacheable() default false;
	
	String cacheName() default "";
	
	boolean transactional();
}
