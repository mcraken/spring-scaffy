package com.scaffy.weave;

import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.AttributeInfo;

public class ClassAnnotationWeavelet extends AnnotationWeavelet{
	
	public ClassAnnotationWeavelet(AnnotationBuilder... annotationBuilders) {
		super(annotationBuilders);
	}

	@Override
	protected void execute(CtClass cc, AttributeInfo attr)
			throws NotFoundException {
		
		cc.getClassFile().addAttribute(attr);
	}

}
