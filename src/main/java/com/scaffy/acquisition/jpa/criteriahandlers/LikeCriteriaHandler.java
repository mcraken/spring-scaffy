package com.scaffy.acquisition.jpa.criteriahandlers;

import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.scaffy.acquisition.exception.BadCriteriaValueException;
import com.scaffy.acquisition.key.RestCriteria;

public class LikeCriteriaHandler implements CriteriaHandler {

	public <T> void handle(RestCriteria restCriteria,
			Map<String, Predicate> criteria, Root<T> root,
			CriteriaBuilder criteriaBuilder) throws BadCriteriaValueException {
		
		Path<String> path =  root.get(restCriteria.readCriteriaName());
		
		Predicate predicate = criteriaBuilder.like(path, restCriteria.getValue());
		
		criteria.put(restCriteria.readCriteriaId(), predicate);
	}

}
