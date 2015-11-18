package com.scaffy.acquisition.hibernatesearch.criteriahandlers;

import java.util.Map;

import org.apache.lucene.search.Query;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.scaffy.acquisition.exception.BadCriteriaValueException;
import com.scaffy.acquisition.exception.CriteriaIDNotFoundException;
import com.scaffy.acquisition.key.RestCriteria;

public class LogicalCriteriaHandler implements CriteriaHandler {

	public <T> void handle(RestCriteria restCriteria,
			Map<String, Query> criteria, QueryBuilder queryBuilder)
			throws BadCriteriaValueException, CriteriaIDNotFoundException {
		
		String logicalOperator = restCriteria.getValue();
		
		Query[] targetQueries = createTargetQueries(restCriteria, criteria);
		
		BooleanJunction<?> booleanJunction = queryBuilder.bool();
		
		if(logicalOperator.equalsIgnoreCase("and")) {

			for(Query targetQuery : targetQueries){
				
				booleanJunction = booleanJunction.must(targetQuery);
			}
			
		}
		else if(logicalOperator.equalsIgnoreCase("or")) {
			
			for(Query targetQuery : targetQueries){
				
				booleanJunction = booleanJunction.should(targetQuery);
			}
		}
		else 
			throw new BadCriteriaValueException(logicalOperator);
		
		criteria.put(restCriteria.readCriteriaId(), booleanJunction.createQuery());
	}

	private Query[] createTargetQueries(RestCriteria restCriteria,
			Map<String, Query> criteria) throws CriteriaIDNotFoundException {
		
		String[] crtsNames = restCriteria.readCriteriaNames(); 
		
		Query[] targetQueries = new Query[crtsNames.length];
		
		Query query = null;
		
		for(int i = 0; i < crtsNames.length; i++){
			
			query = criteria.remove(crtsNames[i]);
			
			if(query == null)
				throw new CriteriaIDNotFoundException(crtsNames[i]);
			
			targetQueries[i] = query;
		}
		return targetQueries;
	}

}
