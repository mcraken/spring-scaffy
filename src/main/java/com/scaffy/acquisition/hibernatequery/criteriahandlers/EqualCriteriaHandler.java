package com.scaffy.acquisition.hibernatequery.criteriahandlers;

import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.scaffy.acquisition.exception.BadCriteriaValueException;
import com.scaffy.acquisition.exception.CriteriaIDNotFoundException;
import com.scaffy.acquisition.key.RestCriteria;

public class EqualCriteriaHandler implements CriteriaHandler {

	public <T> void handle(RestCriteria restCriteria,
			Map<String, Criterion> criteria) throws BadCriteriaValueException,
			CriteriaIDNotFoundException {
		
		criteria.put(restCriteria.readCriteriaId(), Restrictions.eq(
				restCriteria.readCriteriaName(), 
				restCriteria.getParsedValue())
				);
	}

}
