package com.scaffy.weave;

import org.springframework.cache.annotation.CacheEvict;

import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

public class CacheEvictBuilder extends AnnotationBuilder{

	private boolean evictAllEnteries;
	
	public CacheEvictBuilder(boolean evictAllEnteries) {
		
		super(CacheEvict.class.getName());
		
		this.evictAllEnteries = evictAllEnteries;
	}

	@Override
	protected void execute(Annotation annot, ConstPool cpool) {
		
		if(evictAllEnteries)
			addBooleanMemberVariable(annot, cpool, "allEntries", evictAllEnteries);
	}

}
