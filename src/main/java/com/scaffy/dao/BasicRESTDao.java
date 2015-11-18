package com.scaffy.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.WordUtils;

import com.scaffy.controller.MultipartResponse;
import com.scaffy.dao.bean.BeanMethod;
import com.scaffy.dao.bean.BeanTraversalException;
import com.scaffy.dao.bean.BeanVisitor;
import com.scaffy.dao.bean.Node;

public abstract class BasicRESTDao implements RESTDao{

	private Map<BeanMethod.Method, BeanVisitor> visitors;

	public BasicRESTDao() {

		visitors = new HashMap<BeanMethod.Method, BeanVisitor>();
	}

	protected void traverse(
			Object bean, 
			BeanMethod.Method mainMethod) throws BeanTraversalException {

		if(bean.getClass().getAnnotation(Node.class) == null){

			visitors.get(mainMethod).visit(bean);

		} else if(Collection.class.isAssignableFrom(bean.getClass())){
			
			Collection<?> beanAsCollection = (Collection<?>) bean;
			
			for(Object beanEntry : beanAsCollection)
				traverse(beanEntry, mainMethod);
			
		} else {

			keepTraversing(bean, mainMethod);
		}
	}

	private void keepTraversing(Object bean, BeanMethod.Method mainMethod)
			throws BeanTraversalException {
		
		try {

			Map<String, Object> descriptor = PropertyUtils.describe(bean);

			descriptor.remove("class");

			Set<String> properties = descriptor.keySet();

			for(String property : properties){

				String methodName = "get" + WordUtils.capitalize(property);

				BeanMethod beanMethod = bean.getClass().getMethod(methodName).getAnnotation(BeanMethod.class);

				if(beanMethod != null)
					traverse(descriptor.get(property), beanMethod.method());
				else
					traverse(descriptor.get(property), mainMethod); 
			}

		} catch (Exception e) {

			throw new BeanTraversalException(e);
		}
	}

	protected void addVisitor(BeanMethod.Method method, BeanVisitor beanVisitor) {

		visitors.put(method, beanVisitor);
	}


	/* (non-Javadoc)
	 * @see com.scaffy.service.EntityService#create(java.lang.Object)
	 */
	public void create(Object model) throws BeanTraversalException, DaoOperationException {

		execute(model, BeanMethod.Method.POST);
	}

	public void createWithAttachments(MultipartResponse request)
			throws BeanTraversalException, DaoOperationException {
		
		execute(request, BeanMethod.Method.POST);
	}
	
	/* (non-Javadoc)
	 * @see com.scaffy.service.EntityService#update(java.lang.Object)
	 */
	public void update(Object model) throws BeanTraversalException, DaoOperationException {

		execute(model, BeanMethod.Method.PUT);
	}

	/* (non-Javadoc)
	 * @see com.scaffy.service.EntityService#delete(java.lang.Object)
	 */
	public void delete(Object model) throws BeanTraversalException, DaoOperationException {

		execute(model, BeanMethod.Method.DELETE);
	}

	protected abstract void execute(Object model, BeanMethod.Method method)
			throws BeanTraversalException, DaoOperationException;

	protected abstract void execute(MultipartResponse request, BeanMethod.Method method)
			throws BeanTraversalException, DaoOperationException;
}
