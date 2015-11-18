package com.scaffy.acquisition.jpa.criteriahandlers;

import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.scaffy.acquisition.exception.BadCriteriaValueException;
import com.scaffy.acquisition.exception.CriteriaIDNotFoundException;
import com.scaffy.acquisition.key.RestCriteria;

public class LogicalCriteriaHandler implements CriteriaHandler {

	public <T> void handle(RestCriteria restCriteria,
			Map<String, Predicate> criteria, Root<T> root,
			CriteriaBuilder criteriaBuilder) throws BadCriteriaValueException, CriteriaIDNotFoundException {
		
		String[] crtsNames = restCriteria.readCriteriaNames(); 
		
		String logicalOperator = restCriteria.getValue();
		
		Predicate[] targetPredicates = new Predicate[crtsNames.length];
		
		Predicate predicate = null;
		
		for(int i = 0; i < crtsNames.length; i++){
			
			predicate = criteria.remove(crtsNames[i]);
			
			if(predicate == null)
				throw new CriteriaIDNotFoundException(crtsNames[i]);
			
			targetPredicates[i] = predicate;
		}
		
		if(logicalOperator.equalsIgnoreCase("and"))
			criteria.put(restCriteria.readCriteriaId(), criteriaBuilder.and(targetPredicates));
		else if(logicalOperator.equalsIgnoreCase("or"))
			criteria.put(restCriteria.readCriteriaId(), criteriaBuilder.or(targetPredicates));
		else 
			throw new BadCriteriaValueException(logicalOperator);
	}

}
