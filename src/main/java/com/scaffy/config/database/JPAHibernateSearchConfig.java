package com.scaffy.config.database;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class JPAHibernateSearchConfig {

	private static Logger logger = LoggerFactory.getLogger(JPAHibernateSearchConfig.class);

	@Value("${database.reindex}")
	private boolean reindexDatabase;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;
	
	@PostConstruct
	public void init() {

		entityManager = entityManagerFactory.createEntityManager();
		
		if(reindexDatabase){
			
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			
			rebuildDatabaseIndex(fullTextEntityManager);
		}

	}

	private void rebuildDatabaseIndex(
			FullTextEntityManager fullTextEntityManager) {

		try {

			logger.info("Going to rebuild database index");

			fullTextEntityManager.createIndexer().startAndWait();

			logger.info("Rebuilding database index is done!");
			
		}  catch (InterruptedException e) {

			logger.error("Failed to rebuild database index", e);
		}
	}

	@Bean
	@Scope("prototype")
	public FullTextEntityManager fullTextEntityManager() {

		return Search.getFullTextEntityManager(entityManager);
	}
}
