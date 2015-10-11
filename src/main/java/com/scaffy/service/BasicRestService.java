package com.scaffy.service;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

public class BasicRestService implements RestService{
	
	@Autowired
	private EntityManager entityManager;
	
	private Class<?> modelClass;
	
	@Autowired
	private GsonHttpMessageConverter httpMessageConverter;

	@Autowired
	private Validator validator;
	
	public void setModelClass(String modelName) throws ClassNotFoundException {
		this.modelClass = Class.forName(modelName);
	}
	
	private Object bindAndValidate(String jsonBody)
			throws BindException {

		Object model = httpMessageConverter.getGson().fromJson(jsonBody, modelClass);

		BindingResult bindingResult = new MapBindingResult(new HashMap<String, Object>(), modelClass.getSimpleName()); 

		validator.validate(model, bindingResult);

		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);

		return model;
	}
	
	public Object save(String body) throws BindException {
		
		Object model = bindAndValidate(body);
		
		EntityTransaction t = entityManager.getTransaction();
		
		t.begin();
		
		entityManager.persist(model);
		
		t.commit();
		
		return model;
		
	}
	
	public Object update(String body) throws BindException {
		
		Object model = bindAndValidate(body);
		
		EntityTransaction t = entityManager.getTransaction();
		
		t.begin();
		
		entityManager.merge(model);
		
		t.commit();
		
		return model;
	}
	
	public Object delete(String body) throws BindException {
		
		Object model = bindAndValidate(body);
		
		EntityTransaction t = entityManager.getTransaction();
		
		t.begin();
		
		entityManager.remove(model);
		
		t.commit();
		
		return model;
	}

}
