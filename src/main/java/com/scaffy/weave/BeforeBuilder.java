package com.scaffy.weave;

import org.aspectj.lang.annotation.Before;

import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

public class BeforeBuilder extends AnnotationBuilder{

	private String expression;
	
	public BeforeBuilder(String expression) {
		super(Before.class.getName());
		
		this.expression = expression;
	}

	@Override
	protected void execute(Annotation annot, ConstPool cpool) {
		
		addStringMemberVariable(annot, cpool, "value", expression);
	}

}
