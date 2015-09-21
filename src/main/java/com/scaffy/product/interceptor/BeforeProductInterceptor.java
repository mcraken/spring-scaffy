package com.scaffy.product.interceptor;

import javax.annotation.PostConstruct;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@Aspect
public class BeforeProductInterceptor {
	
	private PointInterceptor pointInterceptor;
	
	private Class<?> pointInterceptorClass;
	
	@Autowired
	private ApplicationContext context;
	
	public void setPointInterceptorClass(Class<?> pointInterceptorClass) {
		this.pointInterceptorClass = pointInterceptorClass;
	}
	
	@PostConstruct
	public void init() {
		pointInterceptor = (PointInterceptor) context.getBean(pointInterceptorClass);
	}
	
	public void execute(JoinPoint joinPoint) {
		
		pointInterceptor.executePoint(joinPoint.getArgs()[0]);
	}
}
