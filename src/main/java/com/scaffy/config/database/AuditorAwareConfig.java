package com.scaffy.config.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.scaffy.config.security.SpringSecurityAuditorAware;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditorAwareConfig {
	
	/**
	 * <p>auditorProvider.</p>
	 *
	 * @return a {@link com.informatique.pas.config.database.SpringSecurityAuditorAware} object.
	 */
	@Bean
	public SpringSecurityAuditorAware auditorProvider() {
		
		return new SpringSecurityAuditorAware();
	}
	
}
