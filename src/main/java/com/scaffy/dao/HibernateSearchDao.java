package com.scaffy.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import com.scaffy.acquisition.exception.BadCriteriaValueException;
import com.scaffy.acquisition.exception.CriteriaIDNotFoundException;
import com.scaffy.acquisition.exception.CriteriaNotFoundException;
import com.scaffy.acquisition.exception.InvalidCriteriaException;
import com.scaffy.acquisition.hibernatesearch.criteriahandlers.CriteriaHandler;
import com.scaffy.acquisition.key.RestCriteria;
import com.scaffy.acquisition.key.RestSearchKey;

public class HibernateSearchDao implements SearchDao {

	@Autowired
	private ApplicationContext applicationContext;

	@Value("#{hibernateSearchCriteriaHandlers}")
	private Map<String, CriteriaHandler> criteriaHandlers;

	public <T> List<T> read(RestSearchKey key, Class<T> typeClass)
			throws InvalidCriteriaException {
		
		FullTextEntityManager fullTextEntityManager = applicationContext.getBean(FullTextEntityManager.class);
		
		try{
			
			fullTextEntityManager.getTransaction().begin();
			
			QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
					.buildQueryBuilder().forEntity(typeClass).get();

			Map<String, Query> crtsMap = buildCriteriaMap(key, queryBuilder);
			
			return executeSearchQuery(key, typeClass, fullTextEntityManager, queryBuilder, crtsMap);

		} finally {
			
			fullTextEntityManager.getTransaction().commit();
		}


	}

	private <T>Map<String, Query> buildCriteriaMap(
			RestSearchKey key,
			QueryBuilder queryBuilder
			) throws InvalidCriteriaException {

		Iterator<RestCriteria> crtsIter = key.criteriasIterator();

		if(crtsIter == null)
			return null;

		try{

			Map<String, Query> crtsMap = new HashMap<String, Query>();

			while(crtsIter.hasNext()){

				handleSingleCriteria(key, crtsIter, crtsMap, queryBuilder);
			}

			return crtsMap;

		} catch(Exception e){

			throw new InvalidCriteriaException(e);
		}
	}

	private <T>void handleSingleCriteria(
			RestSearchKey key,
			Iterator<RestCriteria> crtsIter, 
			Map<String, Query> crtsMap,
			QueryBuilder queryBuilder
			)throws InvalidCriteriaException, BadCriteriaValueException, CriteriaIDNotFoundException {

		RestCriteria crt;

		CriteriaHandler handler;

		crt = crtsIter.next();

		handler = criteriaHandlers.get(crt.readCriteriaFunction());

		if(handler == null){

			throw new InvalidCriteriaException(new CriteriaNotFoundException(crt.readCriteriaFunction()));
		}

		handler.handle(crt, crtsMap, queryBuilder);
	}

	private <T> void defineOrder(RestSearchKey key, FullTextQuery fullTextQuery) {

		String[] orderBy = key.getOrders();

		SortField[] sortFields = new SortField[orderBy.length];

		Sort sort = new Sort();

		for(int i = 0; i < orderBy.length; i++) {

			sortFields[i] = key.isDesc() ? new SortField(orderBy[i], SortField.STRING, false) : new SortField(orderBy[i], SortField.STRING, true);
		}		

		sort.setSort(sortFields);

		fullTextQuery.setSort(sort);
	}

	private <T> void definePaging(RestSearchKey key, FullTextQuery fullTextQuery) {

		fullTextQuery.setFirstResult(key.getStart());

		if(key.getCount() != 0)
			fullTextQuery.setMaxResults(key.getCount());
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> executeSearchQuery(
			RestSearchKey key,
			Class<T> typeClass,
			FullTextEntityManager fullTextEntityManager,
			QueryBuilder queryBuilder,
			Map<String, Query> crtsMap
			) {

		Query[] queries = crtsMap.values().toArray(new Query[]{});

		BooleanJunction<?> booleanJunction = queryBuilder.bool();

		for(Query query : queries)
			booleanJunction = booleanJunction.must(query);

		FullTextQuery fullTextQuery =
				fullTextEntityManager.createFullTextQuery(
						booleanJunction.createQuery(), typeClass);

		if(key.hasOrders()){

			defineOrder(key, fullTextQuery);
		}

		definePaging(key, fullTextQuery);

		return fullTextQuery.getResultList(); 

	}

}
