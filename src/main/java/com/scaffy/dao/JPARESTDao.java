package com.scaffy.dao;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;

import com.scaffy.service.bean.BeanTraversal;
import com.scaffy.service.bean.BeanTraversalException;
import com.scaffy.service.bean.BeanVisitor;

public class JPARESTDao implements RESTDao {
	
	private BeanTraversal beanTraversal;
	
	@Autowired
	private EntityManager entityManager;

	@PostConstruct
	public void init() {

		beanTraversal = new BeanTraversal();
	}

	/* (non-Javadoc)
	 * @see com.scaffy.service.EntityService#create(java.lang.Object)
	 */
	public void create(Object model) throws BeanTraversalException {

		EntityTransaction t = entityManager.getTransaction();

		try {

			t.begin();

			beanTraversal.traverse(new BeanVisitor() {

				public void visit(Object bean) {
					entityManager.persist(bean);
				}
			}, model);

			t.commit();

		} catch(BeanTraversalException e) {

			t.rollback();

			throw e;

		} catch (RuntimeException e) {

			t.rollback();

			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see com.scaffy.service.EntityService#update(java.lang.Object)
	 */
	public void update(Object model) throws BeanTraversalException {

		EntityTransaction t = entityManager.getTransaction();

		try {

			t.begin();

			beanTraversal.traverse(new BeanVisitor() {

				public void visit(Object bean) {
					entityManager.merge(bean);
				}
			}, model);

			t.commit();

		}catch(BeanTraversalException e) {

			t.rollback();

			throw e;

		} catch (RuntimeException e) {

			t.rollback();

			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see com.scaffy.service.EntityService#delete(java.lang.Object)
	 */
	public void delete(Object model) throws BeanTraversalException {

		EntityTransaction t = entityManager.getTransaction();

		try{

			t.begin();

			beanTraversal.traverse(new BeanVisitor() {

				public void visit(Object bean) {
					entityManager.remove(bean);
				}
			}, model);

			t.commit();

		}catch(BeanTraversalException e) {

			t.rollback();

			throw e;

		} catch (RuntimeException e) {

			t.rollback();

			throw e;
		}
	}
}
