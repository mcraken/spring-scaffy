package com.scaffy.weave;

import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class RequestMappingBuilder extends AnnotationBuilder{

	private String uri;
	private String method;
	
	public RequestMappingBuilder(String uri) {
		
		this(uri, null);
	}
	
	public RequestMappingBuilder(String uri, String method) {
		
		super(RequestMapping.class.getName());
		
		this.uri = uri;
		this.method = method;
	}
	
	@Override
	protected void execute(Annotation annot, ConstPool cpool) {
		
		addStringArrayMemberVariable(annot, cpool, "value", new String[]{uri});
		
		if(method != null)
			addArrayEnumMemberVariable(annot, cpool, "method", RequestMethod.class.getName(), new String[]{method});
		
	}

}
