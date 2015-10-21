package com.scaffy.acquisition.jpa.criteriahandlers;

import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.scaffy.acquisition.exception.BadCriteriaValueException;
import com.scaffy.acquisition.exception.CriteriaIDNotFoundException;
import com.scaffy.acquisition.key.RestCriteria;

public interface CriteriaHandler {
	
	public <T>void handle(
			RestCriteria restCriteria, 
			Map<String, Predicate> criteria,
			Root<T> root,
			CriteriaBuilder criteriaBuilder
			) throws BadCriteriaValueException, CriteriaIDNotFoundException;
}
