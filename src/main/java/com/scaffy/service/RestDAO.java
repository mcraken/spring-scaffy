package com.scaffy.service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;

import com.scaffy.service.bean.BeanTraversal;
import com.scaffy.service.bean.BeanTraversalException;
import com.scaffy.service.bean.BeanVisitor;

public class RestDAO {
	
	@Autowired
	private EntityManager entityManager;
	
	private BeanTraversal beanTraversal;
	
	@PostConstruct
	public void init() {
		
		beanTraversal = new BeanTraversal();
	}
	
	public void create(Object model) throws BeanTraversalException {

		EntityTransaction t = entityManager.getTransaction();

		t.begin();
		
		beanTraversal.traverse(new BeanVisitor() {

			public void visit(Object bean) {
				entityManager.persist(bean);
			}
		}, model);
		
		t.commit();
	}
	
	public void update(Object model) throws BeanTraversalException {

		EntityTransaction t = entityManager.getTransaction();

		t.begin();
		
		beanTraversal.traverse(new BeanVisitor() {

			public void visit(Object bean) {
				entityManager.merge(bean);
			}
		}, model);
		
		t.commit();
	}
	
	public void delete(Object model) throws BeanTraversalException {

		EntityTransaction t = entityManager.getTransaction();

		t.begin();
		
		beanTraversal.traverse(new BeanVisitor() {

			public void visit(Object bean) {
				entityManager.remove(bean);
			}
		}, model);
		
		t.commit();
	}
}
