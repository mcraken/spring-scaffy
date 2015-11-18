package com.scaffy.product.restful;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Searchable {
	
	public enum DBSearchType {
		IGNORE(""), JPA("db_jpa"), HIBERNATE("db_hibernate");
		
		public String value;
		
		DBSearchType(String value) {
			this.value = value;
		}
	}
	
	public enum FTSearchType {
		IGNORE(""), Hibernate_JPA("ft_hibernate_jpa"), Hibernate_SESSION("ft_hibernate_session");
		
		public String value;
		
		FTSearchType(String value) {
			this.value = value;
		}
	}
	
	DBSearchType dbType() default DBSearchType.IGNORE;
	
	FTSearchType ftType() default FTSearchType.IGNORE;
	
	boolean secure() default false;
	
	String authorization() default "";
	
	boolean cacheable() default false;
	
	String cacheName() default "";
	
}
