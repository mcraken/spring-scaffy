package com.scaffy.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.scaffy.acquisition.exception.BadCriteriaValueException;
import com.scaffy.acquisition.exception.CriteriaIDNotFoundException;
import com.scaffy.acquisition.exception.CriteriaNotFoundException;
import com.scaffy.acquisition.exception.InvalidCriteriaException;
import com.scaffy.acquisition.jpa.criteriahandlers.CriteriaHandler;
import com.scaffy.acquisition.key.RestCriteria;
import com.scaffy.acquisition.key.RestSearchKey;

public class JPASearchDao implements SearchDao {

	@Value("#{jpaCriteriaHandlers}")
	private Map<String, CriteriaHandler> criteriaHandlers;

	@Autowired
	private EntityManager entityManager;
	
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
	
	public <T> List<T> read(RestSearchKey key, Class<T> typeClass)
			throws InvalidCriteriaException {
		
		EntityTransaction t = entityManager.getTransaction();

		try {

			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(typeClass);

			Root<T> root = criteriaQuery.from(typeClass);

			Map<String, Predicate> crtsMap = buildCriteriaMap(key, root, criteriaBuilder);

			return executeSearchQuery(key, criteriaQuery, root, criteriaBuilder, crtsMap);

		} finally {
			
			t.commit();
		}
	}

}
