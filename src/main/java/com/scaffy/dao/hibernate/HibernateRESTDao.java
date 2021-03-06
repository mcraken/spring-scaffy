/**
 * 
 */
package com.scaffy.dao.hibernate;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.scaffy.controller.MultipartResponse;
import com.scaffy.dao.BasicRESTDao;
import com.scaffy.dao.DaoOperationException;
import com.scaffy.dao.bean.BeanMethod;
import com.scaffy.dao.bean.BeanMethod.Method;
import com.scaffy.dao.bean.BeanTraversalException;
import com.scaffy.dao.bean.BeanVisitor;
import com.scaffy.entity.attachment.Attachment;

/**
 * @author 	Sherief Shawky
 * @Email 	mcrakens@gmail.com
 */
public class HibernateRESTDao extends BasicRESTDao {

	@Autowired
	private Session session;
	
	@PostConstruct
	public void init() {
		
		addVisitor(BeanMethod.Method.POST,  new BeanVisitor() {

			public void visit(Object bean) {
				
				session.save(bean);
			}
		});
		
		addVisitor(BeanMethod.Method.PUT,  new BeanVisitor() {

			public void visit(Object bean) {
				
				session.merge(bean);
			}
		});
		
		addVisitor(BeanMethod.Method.DELETE,  new BeanVisitor() {

			public void visit(Object bean) {
				
				bean = session.merge(bean);
				
				session.delete(bean);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.scaffy.dao.RESTDao#read(java.lang.Object, java.lang.Class)
	 */
	public Object read(Object key, Class<?> type) throws DaoOperationException {
		
		Transaction t = session.beginTransaction();
		
		Object model;
		
		try {
			
			t.begin();
			
			model = session.get(type, (Serializable)key);
			
			t.commit();
			
			return model;
			
		} catch(RuntimeException e) {
			
			t.rollback();
			
			throw new DaoOperationException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.scaffy.dao.BasicRESTDao#execute(java.lang.Object, com.scaffy.dao.bean.BeanMethod.Method)
	 */
	@Override
	protected void execute(Object model, Method method)
			throws BeanTraversalException, DaoOperationException {
		
		Transaction t = session.getTransaction();
		
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

	/* (non-Javadoc)
	 * @see com.scaffy.dao.BasicRESTDao#execute(com.scaffy.controller.MultipartResponse, com.scaffy.dao.bean.BeanMethod.Method)
	 */
	@Override
	protected void execute(MultipartResponse request, Method method)
			throws BeanTraversalException, DaoOperationException {
		
		Transaction t = session.getTransaction();
		
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

}
