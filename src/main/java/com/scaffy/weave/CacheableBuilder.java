package com.scaffy.weave;


import org.springframework.cache.annotation.Cacheable;

import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

public class CacheableBuilder extends AnnotationBuilder {

	private String cacheName;
	
	public CacheableBuilder(String cacheName) {
		super(Cacheable.class.getName());
		
		this.cacheName = cacheName;
	}

	@Override
	protected void execute(Annotation annot, ConstPool cpool) {
		addStringArrayMemberVariable(annot, cpool, "value", new String[]{cacheName});
	}

}
