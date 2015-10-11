/**
 * 
 */
package com.scaffy.service.bean;

import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;



/**
 * @author 	Sherief Shawky
 * @Email 	mcrakens@gmail.com
 */
public class BeanTraversal {

	public void traverse(BeanVisitor visitor, Object bean) throws BeanTraversalException {

		if(bean.getClass().getAnnotation(Node.class) == null){

			visitor.visit(bean);

		} else {

			try {

				Map<String, Object> descriptor = PropertyUtils.describe(bean);
				
				descriptor.remove("class");
				
				Set<String> properties = descriptor.keySet();
				
				for(String property : properties){

					traverse(visitor, descriptor.get(property)); 
				}
				
			} catch (Exception e) {

				throw new BeanTraversalException(e);
			}
		}
	}
}
