package com.scaffy.weave;

import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.ConstPool;

public abstract class AnnotationWeavelet {

	private AnnotationBuilder[] annotationBuilders;
	
	public AnnotationWeavelet(AnnotationBuilder... annotationBuilders) {
		this.annotationBuilders = annotationBuilders;
	}

	public void weave(CtClass cc, AnnotationsAttribute attr, ConstPool cpool) throws NotFoundException {
		
		for(AnnotationBuilder builder : annotationBuilders)
			builder.build(attr, cpool);

		execute(cc, attr);
	}
	
	protected abstract void execute(CtClass cc, AttributeInfo attr) throws NotFoundException;
	
}
