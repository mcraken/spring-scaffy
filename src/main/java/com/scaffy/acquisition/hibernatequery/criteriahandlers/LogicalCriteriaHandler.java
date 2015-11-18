package com.scaffy.acquisition.hibernatequery.criteriahandlers;

import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.scaffy.acquisition.exception.BadCriteriaValueException;
import com.scaffy.acquisition.exception.CriteriaIDNotFoundException;
import com.scaffy.acquisition.key.RestCriteria;

public class LogicalCriteriaHandler implements CriteriaHandler {

	public <T> void handle(RestCriteria restCriteria,
			Map<String, Criterion> criteria) throws BadCriteriaValueException,
			CriteriaIDNotFoundException {
		
		String[] crtsNames = restCriteria.readCriteriaNames(); 
		
		String logicalOperator = restCriteria.getValue();
		
		Criterion[] targetCriteia = new Criterion[crtsNames.length];
		
		Criterion criterion = null;
		
		for(int i = 0; i < crtsNames.length; i++){
			
			criterion = criteria.remove(crtsNames[i]);
			
			if(criterion == null)
				throw new CriteriaIDNotFoundException(crtsNames[i]);
			
			targetCriteia[i] = criterion;
		}
		
		if(logicalOperator.equalsIgnoreCase("and"))
			criteria.put(restCriteria.readCriteriaId(), Restrictions.and(targetCriteia));
		else if(logicalOperator.equalsIgnoreCase("or"))
			criteria.put(restCriteria.readCriteriaId(), Restrictions.or(targetCriteia));
		else 
			throw new BadCriteriaValueException(logicalOperator);
	}
	

}
