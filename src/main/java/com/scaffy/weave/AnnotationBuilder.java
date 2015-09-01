package com.scaffy.weave;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

public abstract class AnnotationBuilder {
	
	private String annotationName;

	public AnnotationBuilder(String annotationName) {
		this.annotationName = annotationName;
	}

	protected void addStringMemberVariable(Annotation annot, ConstPool cpool, String propertyName, String value) {

		annot.addMemberValue(propertyName, new StringMemberValue(value, cpool));
	}

	protected void addBooleanMemberVariable(Annotation annot, ConstPool cpool, String propertyName, boolean value) {

		annot.addMemberValue(propertyName, new BooleanMemberValue(value, cpool));
	}
	
	protected void addStringArrayMemberVariable(Annotation annot, ConstPool cpool, String propertyName, String[] values) {

		ArrayMemberValue arrayMemberValue = new ArrayMemberValue(cpool);

		StringMemberValue[] stringMemberValues = new StringMemberValue[values.length];

		for(int i = 0; i < stringMemberValues.length; i++)
			stringMemberValues[i] = new StringMemberValue(values[i], cpool);

		arrayMemberValue.setValue(stringMemberValues);

		annot.addMemberValue(propertyName, arrayMemberValue);
	}

	protected void addEnumMemberVariable(Annotation annot, ConstPool cpool, String propertyName, String type, String value) {

		EnumMemberValue enumMemberValue = createEnumMemberVariable(cpool, type, value);

		annot.addMemberValue(propertyName, enumMemberValue);
	}

	private EnumMemberValue createEnumMemberVariable(ConstPool cpool, String type, String value) {

		EnumMemberValue enumMemberValue = new EnumMemberValue(cpool);

		enumMemberValue.setType(type);

		enumMemberValue.setValue(value);

		return enumMemberValue;
	}

	protected void addArrayEnumMemberVariable(Annotation annot, ConstPool cpool, String propertyName, String type, String[] values) {

		ArrayMemberValue arrayMemberValue = new ArrayMemberValue(cpool);

		EnumMemberValue[] enumMemberValues = new EnumMemberValue[values.length];

		for(int i = 0; i < enumMemberValues.length; i++)
			enumMemberValues[i] = createEnumMemberVariable(cpool, type, values[i]);

		arrayMemberValue.setValue(enumMemberValues);

		annot.addMemberValue(propertyName, arrayMemberValue);
	}

	public void build(AnnotationsAttribute attr, ConstPool cpool) {
		
		Annotation annot = new Annotation(annotationName, cpool);
		
		execute(annot, cpool);
		
		attr.addAnnotation(annot);
	}
	
	protected abstract void execute(Annotation annot, ConstPool cpool);
}
