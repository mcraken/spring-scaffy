package com.scaffy.config.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * <p>SpringSecurityAuditorAware class.</p>
 *
 * @author 	Sherief Shawky
 * @Email 	mcrakens@gmail.com
 *
 * A database auditor aware extracts username information from the
 * current execution context.
 * @version $Id: $Id
 */
public class SpringSecurityAuditorAware implements AuditorAware<String>{

	/**
	 * <p>getCurrentAuditor.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCurrentAuditor() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			return "DEBUG";
		}
		
		return ((User) authentication.getPrincipal()).getUsername();
	}

}
