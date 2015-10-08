package com.scaffy.weave;

import org.springframework.web.bind.annotation.ControllerAdvice;

import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

public class ControllerAdviceBuilder extends AnnotationBuilder {

	public ControllerAdviceBuilder() {
		super(ControllerAdvice.class.getName());
	}

	@Override
	protected void execute(Annotation annot, ConstPool cpool) {
		
	}

}
