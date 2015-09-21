package com.scaffy.weave;

import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

public class JoinPointBuilder extends AnnotationBuilder{

	private String expression;
	
	public JoinPointBuilder(Class<?> target, String targetClassName, String method) {
		
		super(target.getName());
		
		this.expression = "execution(* "
				+ targetClassName
				+ "."
				+ method
				+ "(..))";
	}

	@Override
	protected void execute(Annotation annot, ConstPool cpool) {
		
		addStringMemberVariable(annot, cpool, "value", expression);
	}

}
