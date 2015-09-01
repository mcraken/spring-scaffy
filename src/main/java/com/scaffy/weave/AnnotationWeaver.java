package com.scaffy.weave;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;

public class AnnotationWeaver {

	private String className;

	private AnnotationWeavelet[] annotationWeavelets;

	private String newClassName;
	
	public AnnotationWeaver(
			String className,
			String newClassName,
			AnnotationWeavelet... annotationWeavelets
			) {

		this.newClassName = newClassName;
		
		this.className = className;
		
		this.annotationWeavelets = annotationWeavelets;
	}

	public Class<?> weave() throws NotFoundException, CannotCompileException {

		String pkg = className.substring(0, className.lastIndexOf("."));

		ClassPool cp = ClassPool.getDefault();

		cp.insertClassPath(new ClassClassPath(getClass()));

		CtClass cc = cp.getAndRename(className, newClassName);

		cp.makePackage(cp.getClassLoader(), pkg);

		ClassFile cfile = cc.getClassFile();

		ConstPool cpool = cfile.getConstPool();

		AnnotationsAttribute attr;
		
		for(AnnotationWeavelet annotationWeavelet : annotationWeavelets){
			
			attr = new AnnotationsAttribute(cpool, AnnotationsAttribute.visibleTag);
			
			annotationWeavelet.weave(cc, attr, cpool);
		}
		
		return cc.toClass();

	}

}
