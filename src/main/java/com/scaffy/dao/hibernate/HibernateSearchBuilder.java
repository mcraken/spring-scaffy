package com.scaffy.dao.hibernate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Value;

import com.scaffy.acquisition.exception.BadCriteriaValueException;
import com.scaffy.acquisition.exception.CriteriaIDNotFoundException;
import com.scaffy.acquisition.exception.CriteriaNotFoundException;
import com.scaffy.acquisition.exception.InvalidCriteriaException;
import com.scaffy.acquisition.hibernatesearch.criteriahandlers.CriteriaHandler;
import com.scaffy.acquisition.key.RestCriteria;
import com.scaffy.acquisition.key.RestSearchKey;

public class HibernateSearchBuilder {
	
	@Value("#{hibernateSearchCriteriaHandlers}")
	private Map<String, CriteriaHandler> criteriaHandlers;
	
	public <T>Map<String, Query> buildCriteriaMap(
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

	public <T> void defineOrder(RestSearchKey key, FullTextQuery fullTextQuery) {

		String[] orderBy = key.getOrders();

		SortField[] sortFields = new SortField[orderBy.length];

		Sort sort = new Sort();

		for(int i = 0; i < orderBy.length; i++) {

			sortFields[i] = key.isDesc() ? new SortField(orderBy[i], SortField.STRING, false) : new SortField(orderBy[i], SortField.STRING, true);
		}		

		sort.setSort(sortFields);

		fullTextQuery.setSort(sort);
	}
	
	public <T> void defineOrder(RestSearchKey key, org.hibernate.search.FullTextQuery fullTextQuery) {

		String[] orderBy = key.getOrders();

		SortField[] sortFields = new SortField[orderBy.length];

		Sort sort = new Sort();

		for(int i = 0; i < orderBy.length; i++) {

			sortFields[i] = key.isDesc() ? new SortField(orderBy[i], SortField.STRING, false) : new SortField(orderBy[i], SortField.STRING, true);
		}		

		sort.setSort(sortFields);

		fullTextQuery.setSort(sort);
	}

	public <T> void definePaging(RestSearchKey key, FullTextQuery fullTextQuery) {

		fullTextQuery.setFirstResult(key.getStart());

		if(key.getCount() != 0)
			fullTextQuery.setMaxResults(key.getCount());
	}
	
	public <T> void definePaging(RestSearchKey key, org.hibernate.search.FullTextQuery fullTextQuery) {

		fullTextQuery.setFirstResult(key.getStart());

		if(key.getCount() != 0)
			fullTextQuery.setMaxResults(key.getCount());
	}

	public BooleanJunction<?> createFinalBooleanJunction(
			QueryBuilder queryBuilder, Map<String, Query> crtsMap) {

		Query[] queries = null;

		if(crtsMap != null)
			queries = crtsMap.values().toArray(new Query[]{});

		BooleanJunction<?> booleanJunction = null;

		if(queries != null){

			booleanJunction = queryBuilder.bool();

			for(Query query : queries)
				booleanJunction = booleanJunction.must(query);
		}

		return booleanJunction;
	}
}
