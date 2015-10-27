/**
 * 
 */
package com.scaffy.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author 	Sherief Shawky
 * @Email 	mcrakens@gmail.com
 */
public class ServiceBroker {

	@Autowired
	private ApplicationContext applicationContext;
	
	private Map<String, Object> services;
	
	@PostConstruct
	public void init() {
		services = new HashMap<String, Object>();
	}
	
	@SuppressWarnings("unchecked")
	public <T>T findService(String name, Class<T> type) throws ServiceNotFoundException {
		
		String fullServiceName = type.getSimpleName() + "_" + name;
		
		Object service = services.get(fullServiceName);
		
		if(service == null) {
			
			service = lookupServiceBean(name, type, fullServiceName);
		}
		
		return (T)service;
	}
	
	public <T>T findBean(String name, Class<T> type) throws ServiceNotFoundException{
		
		T bean = applicationContext.getBean(name, type);
		
		if(bean == null)
			throw new ServiceNotFoundException(name);
		
		return bean;
	}

	@SuppressWarnings("unchecked")
	private <T> T lookupServiceBean(String name, Class<T> type,
			String fullServiceName) throws ServiceNotFoundException {
		
		Object service = null;
		
		Map<String, T> beans = applicationContext.getBeansOfType(type);
		
		for(String bean : beans.keySet().toArray(new String[]{})){
			
			if(bean.substring(bean.lastIndexOf("_") + 1).equalsIgnoreCase(name)){
				
				service = beans.get(bean);
				
				services.put(fullServiceName, service);
				
				return (T)service;
			}
				
		}
		
		throw new ServiceNotFoundException(fullServiceName);
	}
}
