package com.scaffy.weave;

import org.springframework.web.bind.annotation.RestController;

import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

public class RestControllerBuilder extends AnnotationBuilder{

	public RestControllerBuilder() {
		super(RestController.class.getName());
	}

	@Override
	protected void execute(Annotation annot, ConstPool cpool) {
		
	}

}
