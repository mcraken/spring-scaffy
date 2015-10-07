package com.scaffy.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.scaffy.query.exception.BadCriteriaValueException;
import com.scaffy.query.exception.CriteriaIDNotFoundException;
import com.scaffy.query.exception.CriteriaNotFoundException;
import com.scaffy.query.exception.InvalidCriteriaException;
import com.scaffy.query.exception.InvalidCriteriaSyntaxException;
import com.scaffy.query.jpa.criteriahandlers.CriteriaHandler;
import com.scaffy.query.key.RestCriteria;
import com.scaffy.query.key.RestSearchKey;

public class BasicRestService implements RestService{
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private EntityManagerFactory emf;
	
	@Value("#{criteriaHandlers}")
	private Map<String, CriteriaHandler> criteriaHandlers;
	
	private Class<?> modelClass;
	
	public void setModelClass(String modelName) throws ClassNotFoundException {
		this.modelClass = Class.forName(modelName);
	}
	
	private <T> List<T> read(RestSearchKey key, Class<T> typeClass) throws InvalidCriteriaException {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(typeClass);
		
		Root<T> root = criteriaQuery.from(typeClass);
		
		Map<String, Predicate> crtsMap = buildCriteriaMap(key, root, criteriaBuilder);
				
		return executeSearchQuery(key, criteriaQuery, root, criteriaBuilder, crtsMap);
		
	}
	
	private <T> List<T> executeSearchQuery(
			RestSearchKey key,
			CriteriaQuery<T> criteriaQuery,
			Root<T> root,
			CriteriaBuilder criteriaBuilder,
			Map<String, Predicate> crtsMap
			) {
		
		criteriaQuery.select(root);
		
		if(crtsMap != null)
			criteriaQuery.where(criteriaBuilder.and(crtsMap.values().toArray(new Predicate[]{})));
		
		if(key.hasOrders()){
			
			defineOrder(key, criteriaQuery, root, criteriaBuilder);
		}
		
		TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
		
		definePaging(key, query);
		
		return query.getResultList(); 

	}

	private <T> void defineOrder(RestSearchKey key,
			CriteriaQuery<T> criteriaQuery, Root<T> root,
			CriteriaBuilder criteriaBuilder) {
		
		String[] orderBy = key.getOrders();
		
		Order[] orders = new Order[orderBy.length];
		
		for(int i = 0; i < orderBy.length; i++)
			orders[i] = key.isDesc() ? criteriaBuilder.desc(root.get(orderBy[i])) : criteriaBuilder.asc(root.get(orderBy[i]));
		
		criteriaQuery.orderBy(orders);
	}

	private <T> void definePaging(RestSearchKey key, TypedQuery<T> query) {
		
		query.setFirstResult(key.getStart());
		
		if(key.getCount() != 0)
			query.setMaxResults(key.getCount());
	}
	private <T>Map<String, Predicate> buildCriteriaMap(
			RestSearchKey key,
			Root<T> root,
			CriteriaBuilder criteriaBuilder
			) throws InvalidCriteriaException {
		
		Iterator<RestCriteria> crtsIter = key.criteriasIterator();

		if(crtsIter == null)
			return null;
		
		try{

			Map<String, Predicate> crtsMap = new HashMap<String, Predicate>();

			while(crtsIter.hasNext()){

				handleSingleCriteria(key, crtsIter, crtsMap, root, criteriaBuilder);
			}

			return crtsMap;

		} catch(Exception e){
			
			throw new InvalidCriteriaException(e);
		}
	}

	private <T>void handleSingleCriteria(
			RestSearchKey key,
			Iterator<RestCriteria> crtsIter, 
			Map<String, Predicate> crtsMap,
			Root<T> root,
			CriteriaBuilder criteriaBuilder
			)throws InvalidCriteriaException, BadCriteriaValueException, CriteriaIDNotFoundException {

		RestCriteria crt;

		CriteriaHandler handler;

		crt = crtsIter.next();

		handler = criteriaHandlers.get(crt.readCriteriaFunction());

		if(handler == null){
			
			throw new InvalidCriteriaException(new CriteriaNotFoundException(crt.readCriteriaFunction()));
		}

		handler.handle(crt, crtsMap, root, criteriaBuilder);
	}
	
	public List<?> query(
			RestSearchKey restSearchKey
			) throws InvalidCriteriaException, InvalidCriteriaSyntaxException,
						NoDataFoundException
	{
		
		List<?> result = read(restSearchKey, modelClass); 
		
		if(result == null || result.size() == 0)
			throw new NoDataFoundException("Nothing matches your key");
		
		return result; 
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
