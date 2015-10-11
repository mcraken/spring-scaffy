package com.scaffy.service;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.scaffy.query.jpa.criteriahandlers.CriteriaHandler;

public class BasicRestService implements RestService{
	
	@Autowired
	private EntityManager entityManager;
	
	@Value("#{criteriaHandlers}")
	private Map<String, CriteriaHandler> criteriaHandlers;
	
	private Class<?> modelClass;
	
	public void setModelClass(String modelName) throws ClassNotFoundException {
		this.modelClass = Class.forName(modelName);
	}
	
	public <T> void save(T model) {
		
		EntityTransaction t = entityManager.getTransaction();
		
		t.begin();
		
		entityManager.persist(model);
		
		t.commit();
		
	}
	
	public <T> void update(T model) {
		
		EntityTransaction t = entityManager.getTransaction();
		
		t.begin();
		
		entityManager.merge(model);
		
		t.commit();
	}
	
	public <T> void delete(T model) {
		
		EntityTransaction t = entityManager.getTransaction();
		
		t.begin();
		
		entityManager.remove(model);
		
		t.commit();
	}

	public Class<?> getModelClass() {
		return modelClass;
	}
}
