package com.scaffy.config.web;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * <p>AnnotationExeclusionStrategy class.</p>
 *
 * @author 	Sherief Shawky
 * @Email 	sheshawky@informatique-eg.com
 *
 * Exclusion strategy used by the JSON marshaler. It excludes any class or filed
 * from serialization once marked with an exclude annotation.
 * @version $Id: $Id
 */
public class AnnotationExeclusionStrategy implements ExclusionStrategy {

	/** {@inheritDoc} */
	public boolean shouldSkipClass(Class<?> f) {
		
		if(f.getAnnotation(Exclude.class) != null){
			return true;
		}
		
		return false;
	}

	/** {@inheritDoc} */
	public boolean shouldSkipField(FieldAttributes f) {
		
		if(f.getAnnotation(Exclude.class) != null){
			return true;
		}
		
		return false;
	}
	
}
