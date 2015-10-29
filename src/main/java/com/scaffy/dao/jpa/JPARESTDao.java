package com.scaffy.dao.jpa;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;

import com.scaffy.controller.MultipartResponse;
import com.scaffy.dao.BasicRESTDao;
import com.scaffy.dao.DaoOperationException;
import com.scaffy.dao.bean.BeanMethod;
import com.scaffy.dao.bean.BeanMethod.Method;
import com.scaffy.dao.bean.BeanTraversalException;
import com.scaffy.dao.bean.BeanVisitor;
import com.scaffy.entity.attachment.Attachment;

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
				
				bean = entityManager.merge(bean);
				
				entityManager.remove(bean);
			}
		});
		
	}

	protected void execute(Object model, BeanMethod.Method method)
			throws BeanTraversalException, DaoOperationException {
		
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

			throw new DaoOperationException(e);
		}
	}

	@Override
	protected void execute(MultipartResponse request, Method method)
			throws BeanTraversalException, DaoOperationException {
		
		EntityTransaction t = entityManager.getTransaction();
		
		try {

			t.begin();

			traverse(request.getModel(), method);
			
			for(Attachment attachment : request.getAttachments()){
				
				traverse(attachment, method);
			}
			
			t.commit();

		} catch(BeanTraversalException e) {

			t.rollback();

			throw e;

		} catch (RuntimeException e) {

			t.rollback();

			throw new DaoOperationException(e);
		}
	}

	public Object read(Object key, Class<?> type) throws DaoOperationException {
		
		EntityTransaction t = entityManager.getTransaction();
		
		Object model;
		
		try {
			
			t.begin();
			
			model = entityManager.find(type, key);
			
			t.commit();
			
			return model;
			
		} catch(RuntimeException e) {
			
			t.rollback();
			
			throw new DaoOperationException(e);
		}
	}
}
