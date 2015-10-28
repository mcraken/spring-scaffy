package com.scaffy.dao.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Exclude class.</p>
 *
 * @author sherief
 * @version $Id: $Id
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BeanMethod {

	public enum Method {
		
		POST, PUT, DELETE;
		
	}
	
	Method method();
}
