package com.scaffy.weave;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AttributeInfo;

public class MethodAnnotationWeavelet extends AnnotationWeavelet{

	private String methodName;
	
	public MethodAnnotationWeavelet(
			String methodName,
			AnnotationBuilder... annotationBuilders) {
		
		super(annotationBuilders);
		
		this.methodName = methodName;
	}

	@Override
	public void execute(CtClass cc, AttributeInfo attr) throws NotFoundException {
		
		CtMethod cMethod = cc.getDeclaredMethod(methodName);
		
		cMethod.getMethodInfo().addAttribute(attr);
		
	}
}
