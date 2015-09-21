package com.scaffy.product.interceptor;

import javax.annotation.PostConstruct;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@Aspect
public class ProductInterceptor {
	
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
	
	public void before(JoinPoint joinPoint) {
		
		((BeforePointInterceptor)pointInterceptor).before(joinPoint.getArgs()[0]);
	}
	
	public void after(JoinPoint joinPoint) {
		
		((AfterPointInterceptor)pointInterceptor).after(joinPoint.getArgs()[0]);
	}
	
	public void around(ProceedingJoinPoint joinPoint) {
		
		try {
			
			before(joinPoint);
			
			joinPoint.proceed();
			
			after(joinPoint);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
}
