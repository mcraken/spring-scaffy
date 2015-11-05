package com.scaffy.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.apache.lucene.search.Query;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.scaffy.acquisition.exception.InvalidCriteriaException;
import com.scaffy.acquisition.key.RestSearchKey;
import com.scaffy.dao.SearchDao;

public class SessionHibernateSearchDao implements SearchDao {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private HibernateSearchBuilder hibernateSearchBuilder;
	
	public <T> List<T> read(RestSearchKey key, Class<T> typeClass)
			throws InvalidCriteriaException {

		FullTextSession fullTextSession = applicationContext.getBean(FullTextSession.class);

		try{

			fullTextSession.getTransaction().begin();

			QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
					.buildQueryBuilder().forEntity(typeClass).get();

			Map<String, Query> crtsMap = hibernateSearchBuilder.buildCriteriaMap(key, queryBuilder);

			return executeSearchQuery(key, typeClass, fullTextSession, queryBuilder, crtsMap);

		} finally {

			fullTextSession.getTransaction().commit();
		}

	}

	@SuppressWarnings("unchecked")
	public <T> List<T> executeSearchQuery(
			RestSearchKey key,
			Class<T> typeClass,
			FullTextSession fullTextEntityManager,
			QueryBuilder queryBuilder,
			Map<String, Query> crtsMap
			) {

		FullTextQuery fullTextQuery = createFullTextQuery(typeClass,
				fullTextEntityManager, queryBuilder, crtsMap);

		if(key.hasOrders()){

			hibernateSearchBuilder.defineOrder(key, fullTextQuery);
		}

		hibernateSearchBuilder.definePaging(key, fullTextQuery);

		return fullTextQuery.list(); 

	}
	
	private <T> FullTextQuery createFullTextQuery(Class<T> typeClass,
			FullTextSession fullTextSession,
			QueryBuilder queryBuilder, Map<String, Query> crtsMap) {
		BooleanJunction<?> booleanJunction = hibernateSearchBuilder.createFinalBooleanJunction(
				queryBuilder, crtsMap);

		FullTextQuery fullTextQuery = null;
		
		if(booleanJunction == null){
			fullTextQuery =
			fullTextSession.createFullTextQuery(queryBuilder.all().createQuery(), typeClass);
		} else {
			
			fullTextQuery =
			fullTextSession.createFullTextQuery(
					booleanJunction.createQuery(), typeClass);
		}
		return fullTextQuery;
	}
}
