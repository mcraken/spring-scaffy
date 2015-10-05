package com.scaffy.repository;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.scaffy.entity.security.ScaffyUser;

@Repository
/**
 * <p>UserRepository interface.</p>
 *
 * @author sherief
 * @version $Id: $Id
 */
public interface UserRepository extends JpaRepository<ScaffyUser, String> {
	
	/**
	 * <p>findByUsernameLike.</p>
	 *
	 * @return PASUser. an object representing the user in case of login success.
	 * A null object in case of failure.
	 *
	 * This method elevates second level caching to speed up the login process.
	 * @param name a {@link java.lang.String} object.
	 */
	@QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
	public ScaffyUser findByUsernameLike(String name);
}
