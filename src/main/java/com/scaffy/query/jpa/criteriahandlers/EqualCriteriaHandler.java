package com.scaffy.query.jpa.criteriahandlers;

import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.scaffy.query.exception.BadCriteriaValueException;
import com.scaffy.query.key.RestCriteria;

public class EqualCriteriaHandler implements CriteriaHandler {

	public <T> void handle(RestCriteria restCriteria,
			Map<String, Predicate> criteria, Root<T> root,
			CriteriaBuilder criteriaBuilder) throws BadCriteriaValueException {
		
		Path<?> path =  root.get(restCriteria.readCriteriaName());
		
		Predicate predicate = criteriaBuilder.equal(path, restCriteria.getParsedValue());
		
		criteria.put(restCriteria.readCriteriaId(), predicate);
	}

}
