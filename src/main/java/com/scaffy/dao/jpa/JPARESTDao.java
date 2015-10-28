package com.scaffy.dao.jpa;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;

import com.scaffy.dao.BasicRESTDao;
import com.scaffy.dao.bean.BeanMethod;
import com.scaffy.dao.bean.BeanTraversalException;
import com.scaffy.dao.bean.BeanVisitor;

public class JPARESTDao extends BasicRESTDao {
	
	@Autowired
	private EntityManager entityManager;

	@PostConstruct
	public void init() {

		addVisitor(BeanMethod.Method.POST,  new BeanVisitor() {

			public void visit(Object bean) {
				
				entityManager.persist(bean);
			}
		});
		
		addVisitor(BeanMethod.Method.PUT,  new BeanVisitor() {

			public void visit(Object bean) {
				
				entityManager.merge(bean);
			}
		});
		
		addVisitor(BeanMethod.Method.DELETE,  new BeanVisitor() {

			public void visit(Object bean) {
				
				entityManager.remove(bean);
			}
		});
		
	}

	protected void execute(Object model, BeanMethod.Method method)
			throws BeanTraversalException {
		
		EntityTransaction t = entityManager.getTransaction();
		
		try {

			t.begin();

			traverse(model, method);

			t.commit();

		} catch(BeanTraversalException e) {

			t.rollback();

			throw e;

		} catch (RuntimeException e) {

			t.rollback();

			throw e;
		}
	}

}
