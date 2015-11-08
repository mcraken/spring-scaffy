package com.scaffy.dao.hibernate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.scaffy.acquisition.exception.BadCriteriaValueException;
import com.scaffy.acquisition.exception.CriteriaIDNotFoundException;
import com.scaffy.acquisition.exception.CriteriaNotFoundException;
import com.scaffy.acquisition.exception.InvalidCriteriaException;
import com.scaffy.acquisition.hibernatequery.criteriahandlers.CriteriaHandler;
import com.scaffy.acquisition.key.RestCriteria;
import com.scaffy.acquisition.key.RestSearchKey;
import com.scaffy.dao.SearchDao;

public class HibernateQueryDao implements SearchDao{

	@Value("#{hibernateQueryCriteriaHandlers}")
	private Map<String, CriteriaHandler> criteriaHandlers;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	private <T> List<T> executeSearchQuery(
			RestSearchKey key,
			Criteria criteria,
			Map<String, Criterion> crtsMap
			) {

		if(crtsMap != null){
			
			Criterion[] crts = crtsMap.values().toArray(new Criterion[]{});
			
			for(Criterion critrion : crts)
				criteria.add(critrion);	
		}

		if(key.hasOrders()){

			defineOrder(key, criteria);
		}

		definePaging(key, criteria);

		return (List<T>)criteria.list(); 

	}
	
	private <T> void defineOrder(RestSearchKey key, Criteria criteria) {

		String[] orderBy = key.getOrders();

		Order[] orders = new Order[orderBy.length];

		for(int i = 0; i < orderBy.length; i++)
			orders[i] = key.isDesc() ? Order.desc(orderBy[i]) : Order.asc(orderBy[i]);
		
		for(Order order : orders)
			criteria.addOrder(order);
			
	}
	
	private void definePaging(RestSearchKey key, Criteria criteria) {

		criteria.setFirstResult(key.getStart());

		if(key.getCount() != 0)
			criteria.setMaxResults(key.getCount());
	}
	
	private Map<String, Criterion> buildCriteriaMap(RestSearchKey key) throws InvalidCriteriaException {

		Iterator<RestCriteria> crtsIter = key.criteriasIterator();

		if(crtsIter == null)
			return null;

		try{

			Map<String, Criterion> crtsMap = new HashMap<String, Criterion>();

			while(crtsIter.hasNext()){

				handleSingleCriteria(key, crtsIter, crtsMap);
			}

			return crtsMap;

		} catch(Exception e){

			throw new InvalidCriteriaException(e);
		}
	}
	
	private void handleSingleCriteria(
			RestSearchKey key,
			Iterator<RestCriteria> crtsIter, 
			Map<String, Criterion> crtsMap
			)throws InvalidCriteriaException, BadCriteriaValueException, CriteriaIDNotFoundException {

		RestCriteria crt;

		CriteriaHandler handler;

		crt = crtsIter.next();

		handler = criteriaHandlers.get(crt.readCriteriaFunction());

		if(handler == null){

			throw new InvalidCriteriaException(new CriteriaNotFoundException(crt.readCriteriaFunction()));
		}

		handler.handle(crt, crtsMap);
	}
	
	public <T> List<T> read(RestSearchKey key, Class<T> typeClass)
			throws InvalidCriteriaException {
		
		Transaction t = sessionFactory.getCurrentSession().getTransaction();

		try {

			t.begin();
			
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(typeClass);

			Map<String, Criterion> crtsMap = buildCriteriaMap(key);

			return executeSearchQuery(key, criteria, crtsMap);

		} finally {
			
			t.commit();
		}
		
	}

}
