package com.scaffy.config.database;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SessionHibernateSearchConfig {

	private static Logger logger = LoggerFactory.getLogger(SessionHibernateSearchConfig.class);

	@Value("${database.reindex}")
	private boolean reindexDatabase;

	@Autowired
	private Session session;

	@PostConstruct
	public void init() {

		if(reindexDatabase){

			Transaction t = null;

			try{

				t = session.beginTransaction();

				FullTextSession fullTextSession = Search.getFullTextSession(session);

				rebuildDatabaseIndex(fullTextSession);
				
			} finally {
				
				if(t != null)
					t.commit();
			}

		}

	}

	private void rebuildDatabaseIndex(
			FullTextSession fullTextSession) {

		try {

			logger.info("Going to rebuild database index");

			fullTextSession.createIndexer().startAndWait();

			logger.info("Rebuilding database index is done!");

		}  catch (InterruptedException e) {

			logger.error("Failed to rebuild database index", e);
		}
	}

	@Bean
	@Scope("prototype")
	public FullTextSession fullTextSession() {

		return Search.getFullTextSession(session);
	}
}
