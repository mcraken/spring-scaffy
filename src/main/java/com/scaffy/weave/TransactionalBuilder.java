package com.scaffy.weave;

import javax.transaction.Transaction;

import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

public class TransactionalBuilder extends AnnotationBuilder {

	public TransactionalBuilder() {
		super(Transaction.class.getName());
	}

	@Override
	protected void execute(Annotation annot, ConstPool cpool) {
		
	}

}
