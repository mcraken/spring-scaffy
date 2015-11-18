package com.scaffy.acquisition.hibernatesearch.criteriahandlers;

import java.util.Map;

import org.apache.lucene.search.Query;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.scaffy.acquisition.exception.BadCriteriaValueException;
import com.scaffy.acquisition.exception.CriteriaIDNotFoundException;
import com.scaffy.acquisition.key.RestCriteria;

public class EqualCriteriaHandler implements CriteriaHandler {

	public <T> void handle(RestCriteria restCriteria,
			Map<String, Query> criteria, QueryBuilder queryBuilder)
			throws BadCriteriaValueException, CriteriaIDNotFoundException {
		
		Query query = queryBuilder.keyword().onField(restCriteria.readCriteriaName()).matching(restCriteria.getParsedValue()).createQuery();
		
		criteria.put(restCriteria.readCriteriaId(), query);
	}

}
