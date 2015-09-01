package com.scaffy.weave;

import org.springframework.security.access.prepost.PreAuthorize;

import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

public class PreAuthorizeBuilder extends AnnotationBuilder{

	private String priv;

	public PreAuthorizeBuilder(String priv) {
		
		super(PreAuthorize.class.getName());

		this.priv = priv;
	}

	@Override
	protected void execute(Annotation annot, ConstPool cpool) {
		
		addStringMemberVariable(annot, cpool, "value", priv);
	}

}
