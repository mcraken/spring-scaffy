package com.scaffy.acquisition.hibernatequery.criteriahandlers;

import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.scaffy.acquisition.exception.BadCriteriaValueException;
import com.scaffy.acquisition.exception.CriteriaIDNotFoundException;
import com.scaffy.acquisition.key.RestCriteria;

public interface CriteriaHandler {
	
	public <T>void handle(
			RestCriteria restCriteria, 
			Map<String, Criterion> criteria
			) throws BadCriteriaValueException, CriteriaIDNotFoundException;
}
